package com.app.food_booking_backend.model.dto;

import com.app.food_booking_backend.model.entity.enums.TableStatus;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class TableDTO {
    private String id;
    private String tableNumber;
    private String description;
    private Integer capacity;
    private TableStatus status;
} 