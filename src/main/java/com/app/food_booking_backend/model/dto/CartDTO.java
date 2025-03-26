package com.app.food_booking_backend.model.dto;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CartDTO {
    private String uuid;
    private String userUuid;
    @Builder.Default
    private List<CartItemDTO> items = new ArrayList<>();
    private double totalAmount;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
