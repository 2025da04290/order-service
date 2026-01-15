package com.vk.orderservice.repository;

import com.vk.orderservice.entity.Order;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface OrderRepository extends JpaRepository<Order, Long> {

    Optional<Order> findByOrderId(String orderId);

    List<Order> findByUserId(String userId);

    List<Order> findByUserIdAndStatus(String userId, Order.OrderStatus status);

    List<Order> findByStatus(Order.OrderStatus status);

    boolean existsByOrderId(String orderId);
}
