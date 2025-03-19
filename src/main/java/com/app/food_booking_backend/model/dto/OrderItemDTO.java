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
public class OrderItemDTO {
    private String uuid;
    private String foodUuid;
    private String foodName;
    private int quantity;
    private BigDecimal price;
}
