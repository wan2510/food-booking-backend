package com.app.food_booking_backend.model.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CartUpdateRequestDTO {
    private String cartItemId;
    private int quantity;
}
