package com.app.food_booking_backend.controller;

import java.util.List;
import java.util.Map;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.app.food_booking_backend.model.dto.OrderDTO;
import com.app.food_booking_backend.service.OrderService;

@RestController
@RequestMapping("/api/order")
@CrossOrigin(origins = "http://localhost:5173", allowCredentials = "true")
public class OrderController {

    private final OrderService orderService;

    public OrderController(OrderService orderService) {
        this.orderService = orderService;
    }

    @GetMapping("/{uuid}")
    public ResponseEntity<?> getOrderByUuid(@PathVariable String uuid) {
        try {
            OrderDTO orderDTO = orderService.getOrderByUuid(uuid);
            return ResponseEntity.ok(orderDTO);
        } catch (RuntimeException ex) {
            return ResponseEntity.badRequest().body(Map.of("errorMessage", ex.getMessage()));
        }
    }

    @GetMapping("/user/{userUuid}")
    public ResponseEntity<?> getOrdersByUser(@PathVariable String userUuid) {
        try {
            List<OrderDTO> orders = orderService.getOrdersByUser(userUuid);
            return ResponseEntity.ok(orders);
        } catch (RuntimeException ex) {
            return ResponseEntity.badRequest().body(Map.of("errorMessage", ex.getMessage()));
        }
    }

    @PostMapping
    public ResponseEntity<?> createOrder(@RequestBody OrderDTO orderDTO) {
        try {
            OrderDTO createdOrder = orderService.createOrder(orderDTO);
            return ResponseEntity.ok(createdOrder);
        } catch (RuntimeException ex) {
            return ResponseEntity.badRequest().body(Map.of("errorMessage", ex.getMessage()));
        }
    }

    @PutMapping("/{uuid}/status")
    public ResponseEntity<?> updateOrderStatus(
            @PathVariable String uuid,
            @RequestBody Map<String, String> request
    ) {
        String status = request.get("status");
        if (status == null || status.isBlank()) {
            return ResponseEntity.badRequest().body(Map.of("error", "Trạng thái không hợp lệ"));
        }
        try {
            OrderDTO updatedOrder = orderService.updateOrderStatus(uuid, status);
            return ResponseEntity.ok(updatedOrder);
        } catch (RuntimeException ex) {
            return ResponseEntity.badRequest().body(Map.of("errorMessage", ex.getMessage()));
        }
    }
}
