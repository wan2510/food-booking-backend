package com.app.food_booking_backend.model.response;

import lombok.Data;

@Data
public class FoodResponse {
    private Long id;
    private String name;
    private String description;
    private double price;
    private int rating;
}
