package com.app.food_booking_backend.model.dto;

import lombok.Data;

@Data
public class RestaurantTableDTO {
    private Integer number;
    private String description;
    private Boolean status;
    private Integer maxNumberHuman;
}
