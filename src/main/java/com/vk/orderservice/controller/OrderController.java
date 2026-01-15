package com.vk.orderservice.controller;

import com.vk.orderservice.dto.OrderResponse;
import com.vk.orderservice.service.OrderService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping
@Tag(name = "Order", description = "Order API")
public class OrderController {

    private final OrderService orderService;

    public OrderController(OrderService orderService) {
        this.orderService = orderService;
    }

    @GetMapping("/getOrder")
    @Operation(
            summary = "Get Order",
            description = "Returns an Order ID (requires authentication)",
            security = @SecurityRequirement(name = "bearerAuth")
    )
    public ResponseEntity<OrderResponse> getOrder(
            @RequestHeader(value = "X-Auth-User", required = false) String authUser) {

        OrderResponse response = orderService.getOrder();
        return ResponseEntity.ok(response);
    }
}
