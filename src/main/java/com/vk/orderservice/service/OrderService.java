package com.vk.orderservice.service;

import com.vk.orderservice.dto.CreateOrderRequest;
import com.vk.orderservice.dto.OrderResponse;
import com.vk.orderservice.entity.Order;
import com.vk.orderservice.exception.OrderNotFoundException;
import com.vk.orderservice.repository.OrderRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class OrderService {

    private final OrderRepository orderRepository;

    public OrderService(OrderRepository orderRepository) {
        this.orderRepository = orderRepository;
    }

    @Transactional
    public OrderResponse createOrder(String userId, CreateOrderRequest request) {
        String orderId = generateOrderId();

        Order order = Order.builder()
                .orderId(orderId)
                .userId(userId)
                .description(request.getDescription())
                .amount(request.getAmount())
                .status(Order.OrderStatus.PENDING)
                .build();

        Order savedOrder = orderRepository.save(order);
        return mapToResponse(savedOrder);
    }

    public OrderResponse getOrder(String orderId) {
        Order order = orderRepository.findByOrderId(orderId)
                .orElseThrow(() -> new OrderNotFoundException("Order not found: " + orderId));
        return mapToResponse(order);
    }

    public List<OrderResponse> getOrdersByUser(String userId) {
        return orderRepository.findByUserId(userId).stream()
                .map(this::mapToResponse)
                .collect(Collectors.toList());
    }

    public List<OrderResponse> getAllOrders() {
        return orderRepository.findAll().stream()
                .map(this::mapToResponse)
                .collect(Collectors.toList());
    }

    @Transactional
    public OrderResponse updateOrderStatus(String orderId, String status) {
        Order order = orderRepository.findByOrderId(orderId)
                .orElseThrow(() -> new OrderNotFoundException("Order not found: " + orderId));

        order.setStatus(Order.OrderStatus.valueOf(status.toUpperCase()));
        Order updatedOrder = orderRepository.save(order);
        return mapToResponse(updatedOrder);
    }

    @Transactional
    public void deleteOrder(String orderId) {
        Order order = orderRepository.findByOrderId(orderId)
                .orElseThrow(() -> new OrderNotFoundException("Order not found: " + orderId));
        orderRepository.delete(order);
    }

    private String generateOrderId() {
        return "ORD-" + UUID.randomUUID().toString().substring(0, 8).toUpperCase();
    }

    private OrderResponse mapToResponse(Order order) {
        return OrderResponse.builder()
                .id(order.getId())
                .orderId(order.getOrderId())
                .userId(order.getUserId())
                .description(order.getDescription())
                .amount(order.getAmount())
                .status(order.getStatus().name())
                .createdAt(order.getCreatedAt())
                .updatedAt(order.getUpdatedAt())
                .build();
    }
}
