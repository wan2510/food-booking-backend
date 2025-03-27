package com.app.food_booking_backend.model.response;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class FoodResponse {
    private String uuid;          // 🔥 Đổi từ "id" thành "uuid"
    private String name;
    private String description;
    private String status;
    private String categoryId;
    private String categoryName;
    private String imageUrl;
    private BigDecimal price;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
