package com.order.service;

import com.order.model.Order;
import com.order.repository.OrderRepository;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;


@Service
@RequiredArgsConstructor
public class KafkaConsumerService {

    private final OrderRepository orderRepository;

    private static final Logger log = LoggerFactory.getLogger(KafkaConsumerService.class);

    @KafkaListener(topics = "${spring.kafka.topic.name}", groupId = "order-group", containerFactory = "kafkaListenerContainerFactory")
    public void consume(Order order) {
        log.info("Consumed order from Kafka: {} ", order);
        orderRepository.save(order)
                .doOnSuccess(successfulOrder -> log.info("Order saved to Mongo DB:{}", successfulOrder))
                .doOnError(error -> log.error("Error hile saving the order", error))
                .subscribe();

    }
}
