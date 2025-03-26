package com.app.food_booking_backend.model.dto;

import java.time.LocalDateTime;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CartItemDTO {
    private String uuid;
    private FoodDTO food;
    private int quantity;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
