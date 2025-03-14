package com.app.food_booking_backend.model.dto;

import java.math.BigDecimal;

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
public class FoodDTO {
    private String uuid;
    private String name;
    private String description;
    private String status;
    private String imageUrl;
    private BigDecimal price;
    private String categoryName;
}