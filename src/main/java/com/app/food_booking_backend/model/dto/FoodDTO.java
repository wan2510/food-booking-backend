package com.app.food_booking_backend.model.dto;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class FoodDTO {
    private String uuid;
    private String name;
    private String description;
    private String status;
    private String categoryId;
    private String imageUrl;
    private BigDecimal price;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
