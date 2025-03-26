package com.app.food_booking_backend.service;

import com.app.food_booking_backend.model.dto.OrderDTO;
import com.app.food_booking_backend.model.entity.Order;
import com.app.food_booking_backend.model.entity.User;
import com.app.food_booking_backend.model.entity.enums.OrderStatus;
import com.app.food_booking_backend.repository.OrderRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.UUID;

@Service
public class OrderService {

    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private NotificationService notificationService;

    @Autowired
    private UserService userService;

    public OrderDTO createOrder(OrderDTO orderDTO) {
        User user = userService.findByUuid(orderDTO.getUserId());
        if (user == null) {
            throw new RuntimeException("User not found with ID: " + orderDTO.getUserId());
        }

        Order order = Order.builder()
                .uuid(UUID.randomUUID().toString())
                .user(user)
                .totalPrice(orderDTO.getTotalPrice())
                .status(OrderStatus.COMPLETED)
                .createdAt(LocalDateTime.now())
                .updatedAt(LocalDateTime.now())
                .build();

        orderRepository.save(order);

        String adminId = "550e8400-e29b-41d4-a716-446655440000";
        notificationService.createAndSendNotification(
                adminId,
                "ORDER_SUCCESS",
                "A new order has been placed successfully! Order ID: " + order.getUuid()
        );

        return convertToDTO(order);
    }

    private OrderDTO convertToDTO(Order order) {
        OrderDTO dto = new OrderDTO();
        dto.setUuid(order.getUuid());
        dto.setUserId(order.getUser().getUuid());
        dto.setTotalPrice(order.getTotalPrice());
        dto.setStatus(order.getStatus());
        dto.setCreatedAt(order.getCreatedAt());
        dto.setUpdatedAt(order.getUpdatedAt());
        return dto;
    }
}