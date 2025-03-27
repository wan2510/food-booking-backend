package com.app.food_booking_backend.model.dto;

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
public class TableRestaurantDTO {
    private Integer id;
    private String tableNumber;
    private int capacity;
    private int bookedGuests;
    private String status;
    private String type;
}