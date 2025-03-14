package com.app.food_booking_backend.util;

import com.app.food_booking_backend.model.dto.FoodDTO;
import com.app.food_booking_backend.model.entity.FoodEntity;

public class FoodMapper {
    public static FoodDTO toDTO(FoodEntity entity) {
        return FoodDTO.builder()
                .id(entity.getId())
                .name(entity.getName())
                .description(entity.getDescription())
                .price(entity.getPrice())
                .rating(entity.getRating())
                .build();
    }
}
