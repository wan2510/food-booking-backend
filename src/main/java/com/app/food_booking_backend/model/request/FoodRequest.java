package com.app.food_booking_backend.model.request;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class FoodRequest {
    private String name;
    private String description;
    private double price;
    private double rating;
}
