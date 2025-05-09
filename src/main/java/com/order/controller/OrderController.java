package com.order.controller;

import com.order.model.Order;
import com.order.repository.OrderRepository;
import com.order.service.KafkaProducerService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/orders")
@RequiredArgsConstructor
public class OrderController {

    private final OrderRepository orderRepository;
    private final KafkaProducerService kafkaProducerService;

    @GetMapping
    public Flux<Order> getAllOrders() {
        return orderRepository.findAll();
    }

    @GetMapping("/{id}")
    public Mono<Order> getOrderById(@PathVariable String id) {
        return orderRepository.findById(id);
    }

    @PostMapping
    public Mono<String> placeOrder(@Valid @RequestBody Order order) {
        return kafkaProducerService.sendOrder(order);

    }

}
