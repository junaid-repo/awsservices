package com.cloud.ecommerce.ordertracking.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;

@Entity
@Table
@Data
public class CustomerOrder {

    @Id
    @GeneratedValue(strategy = jakarta.persistence.GenerationType.AUTO)
    public Long orderId;

    private Long customerId;
    private String productName;
    private Integer quantity;
    private Double price;
    private String orderStatus; // e.g., "Pending", "Shipped", "Delivered"
    private String shippingAddress;

    private String orderDate; // Consider using LocalDate or LocalDateTime for better date handling
}
