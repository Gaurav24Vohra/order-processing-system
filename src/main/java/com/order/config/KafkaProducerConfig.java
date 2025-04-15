package com.order.config;

import com.order.model.Order;
import org.springframework.boot.autoconfigure.kafka.KafkaProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.core.DefaultKafkaProducerFactory;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.core.ProducerFactory;

import java.util.Map;

@Configuration
public class KafkaProducerConfig {

    private final KafkaProperties kafkaProperties;

    public KafkaProducerConfig(KafkaProperties kafkaProperties) {
        this.kafkaProperties = kafkaProperties;
    }

//    @Bean
//    public ReactiveKafkaProducerTemplate<String, Order> reactiveKafkaProducerTemplate() {
//        SenderOptions<String, Order> senderOptions = SenderOptions.create(kafkaProperties.buildProducerProperties());
//        return new ReactiveKafkaProducerTemplate<>(senderOptions);
//    }

    @Bean
    public ProducerFactory<String, Order> producerFactory(){
        Map<String, Object> props = kafkaProperties.buildProducerProperties();
        return new DefaultKafkaProducerFactory<>(props);
    }


    @Bean
    public KafkaTemplate<String, Order> kafkaTemplate(){
        return new KafkaTemplate<>(producerFactory());
    }
}
