package com.app.food_booking_backend.model.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CartRequestDTO {
    private String userUuid;
    private String foodUuid;
    private int quantity;
}