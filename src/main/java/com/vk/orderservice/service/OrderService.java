package com.vk.orderservice.service;

import com.vk.orderservice.dto.OrderResponse;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class OrderService {

    public OrderResponse getOrder() {
        String orderId = "ORD-" + UUID.randomUUID().toString().substring(0, 8).toUpperCase();

        return OrderResponse.builder()
                .orderId(orderId)
                .build();
    }
}
