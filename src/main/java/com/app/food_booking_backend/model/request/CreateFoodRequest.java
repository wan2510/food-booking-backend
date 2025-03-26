package com.app.food_booking_backend.model.request;

import java.math.BigDecimal;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CreateFoodRequest {
    private String name;
    private String description;
    private String status="Available";
    private String categoryId="9c155bc0-fe9f-11ef-af83-d8bbc1af34c0";
    private String imageUrl;
    private BigDecimal price;
}
