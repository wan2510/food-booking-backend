package com.app.food_booking_backend.model.request;

import java.math.BigDecimal;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UpdateFoodRequest {
    private String name;
    private String description;
    private String status;
    private String categoryId;
    private String imageUrl;
    private BigDecimal price;
}
