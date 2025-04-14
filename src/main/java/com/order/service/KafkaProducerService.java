package com.order.service;

import com.order.model.Order;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

@Service
@RequiredArgsConstructor
public class KafkaProducerService {

    private final KafkaTemplate<String, Order> kafkaTemplate;

    @Value("${kafka.topic.name}")
    private String topic;


    public Mono<String> sendOrder(Order order){
        return Mono.fromRunnable(() ->
                kafkaTemplate.send(topic, order.getId(), order));
    }
}
