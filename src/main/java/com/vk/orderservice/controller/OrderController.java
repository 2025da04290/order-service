package com.vk.orderservice.controller;

import com.vk.orderservice.dto.CreateOrderRequest;
import com.vk.orderservice.dto.OrderResponse;
import com.vk.orderservice.service.OrderService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping
@Tag(name = "Order", description = "Order Management API")
public class OrderController {

    private final OrderService orderService;

    public OrderController(OrderService orderService) {
        this.orderService = orderService;
    }

    @PostMapping
    @Operation(
            summary = "Create Order",
            description = "Create a new order",
            security = @SecurityRequirement(name = "bearerAuth")
    )
    public ResponseEntity<OrderResponse> createOrder(
            @RequestHeader(value = "X-Auth-User", defaultValue = "anonymous") String authUser,
            @Valid @RequestBody CreateOrderRequest request) {

        OrderResponse response = orderService.createOrder(authUser, request);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @GetMapping("/{orderId}")
    @Operation(
            summary = "Get Order by ID",
            description = "Get order details by order ID",
            security = @SecurityRequirement(name = "bearerAuth")
    )
    public ResponseEntity<OrderResponse> getOrder(@PathVariable String orderId) {
        OrderResponse response = orderService.getOrder(orderId);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/getOrder")
    @Operation(
            summary = "Get Orders for User",
            description = "Get all orders for the authenticated user",
            security = @SecurityRequirement(name = "bearerAuth")
    )
    public ResponseEntity<List<OrderResponse>> getOrdersForUser(
            @RequestHeader(value = "X-Auth-User", defaultValue = "anonymous") String authUser) {

        List<OrderResponse> orders = orderService.getOrdersByUser(authUser);
        return ResponseEntity.ok(orders);
    }

    @GetMapping
    @Operation(
            summary = "Get All Orders",
            description = "Get all orders (admin only)",
            security = @SecurityRequirement(name = "bearerAuth")
    )
    public ResponseEntity<List<OrderResponse>> getAllOrders() {
        List<OrderResponse> orders = orderService.getAllOrders();
        return ResponseEntity.ok(orders);
    }

    @PatchMapping("/{orderId}/status")
    @Operation(
            summary = "Update Order Status",
            description = "Update the status of an order",
            security = @SecurityRequirement(name = "bearerAuth")
    )
    public ResponseEntity<OrderResponse> updateOrderStatus(
            @PathVariable String orderId,
            @RequestParam String status) {

        OrderResponse response = orderService.updateOrderStatus(orderId, status);
        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/{orderId}")
    @Operation(
            summary = "Delete Order",
            description = "Delete an order",
            security = @SecurityRequirement(name = "bearerAuth")
    )
    public ResponseEntity<Void> deleteOrder(@PathVariable String orderId) {
        orderService.deleteOrder(orderId);
        return ResponseEntity.noContent().build();
    }
}
