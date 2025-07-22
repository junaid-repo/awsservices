package com.cloud.ecommerce.ordertracking.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class OrderStatus {

    private String orderId;
    private String orderStatus;
}
//{"orderId":"252","orderStatus":"orderPlaced"}