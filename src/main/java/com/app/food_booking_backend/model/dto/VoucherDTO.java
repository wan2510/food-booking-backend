package com.app.food_booking_backend.model.dto;

import com.app.food_booking_backend.model.entity.enums.*;

import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class VoucherDTO {
    private String id;
    private String code; 
    private String name;
    private int discountPercentage;
    private int maxDiscountValue;
    private int minOrderValue;
    private LocalDateTime expiredAt;
    private VoucherStatusEnum status;
    private VoucherTypeEnum type;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt; 
    private LocalDateTime deletedAt; 
}