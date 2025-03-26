package com.app.food_booking_backend.util;

import com.app.food_booking_backend.model.dto.FoodDTO;
import com.app.food_booking_backend.model.entity.Food;

public class FoodMapper {
    public static FoodDTO toDTO(Food entity) {
        FoodDTO dto = new FoodDTO();
        dto.setUuid(entity.getUuid());
        dto.setName(entity.getName());
        dto.setDescription(entity.getDescription());
        dto.setStatus(entity.getStatus());
        dto.setPrice(entity.getPrice());
        dto.setImageUrl(entity.getImageUrl());
        dto.setCategoryId(entity.getCategory().getUuid());
        // dto.setCategoryName(entity.getCategory().getName());
        dto.setCreatedAt(entity.getCreatedAt());
        dto.setUpdatedAt(entity.getUpdatedAt());
        return dto;
    }
}
