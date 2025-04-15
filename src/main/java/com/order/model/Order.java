package com.order.model;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.Instant;

@Document(collection = "orders")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Order {

    @Id
    private String id;

    private String customerId;

    @NotBlank(message = "Product name must not be blank")
    private String product;

    @Min(value = 1, message = "Quantity must be at least 1")
    private int quantity;

    private OrderStatus status;

    private Instant createdAt;
}
