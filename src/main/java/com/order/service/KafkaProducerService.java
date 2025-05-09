package com.order.service;

import com.order.model.Order;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

@Service
public class KafkaProducerService {

    private final KafkaTemplate<String, Order> kafkaTemplate;

    public KafkaProducerService(KafkaTemplate<String, Order> kafkaTemplate) {
        this.kafkaTemplate = kafkaTemplate;
    }

    @Value("${spring.kafka.topic.name}")
    private String topic;


    public Mono<String> sendOrder(Order order) {
        return Mono.fromFuture(() -> kafkaTemplate.send(topic, order.getId(), order).toCompletableFuture())
                .map(result -> "Order sent successfully. Offset: " + result.getRecordMetadata().offset())
                .onErrorResume(e -> Mono.just("Failed to send order: " + e.getMessage()));
    }


}
