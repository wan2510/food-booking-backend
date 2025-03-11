package com.app.food_booking_backend.model.dto;

import com.app.food_booking_backend.model.entity.enums.VoucherStatusEnum;
import com.app.food_booking_backend.model.entity.enums.VoucherTypeEnum;
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
    private int discount;
    private int maxDiscountValue;
    private int minOrderValue;
    private LocalDateTime expiredAt;
    private VoucherStatusEnum status;
    private VoucherTypeEnum type;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt; 
}
