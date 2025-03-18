package com.app.food_booking_backend.model.dto;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

import com.app.food_booking_backend.model.entity.enums.OrderStatus;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class OrderDTO {
    private String uuid;
    private String userUuid;
    private BigDecimal totalPrice;
    private OrderStatus status; 
    private List<OrderItemDTO> items;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
