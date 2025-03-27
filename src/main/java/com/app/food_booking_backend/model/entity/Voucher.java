package com.app.food_booking_backend.model.entity;

import jakarta.persistence.*;
import lombok.*;

import com.app.food_booking_backend.model.entity.enums.VoucherStatusEnum;
import com.app.food_booking_backend.model.entity.enums.VoucherTypeEnum;

import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Voucher {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "id", length = 36, nullable = false, unique = true, updatable = false)
    private String id;    

    @Column(name = "name", length = 255, nullable = false, updatable = false)
    private String name;

    @Column(name = "code", length = 50, nullable = false, unique = true, updatable = false)
    private String code;

    @Column(name = "discount_percentage", nullable = false)
    private int discountPercentage;

    @Column(name = "max_discount_value", nullable = false)
    private int maxDiscountValue;

    @Column(name = "min_order_value", nullable = false)
    private int minOrderValue;

    @Column(name = "expired_at", nullable = false, updatable = false)
    private LocalDateTime expiredAt;

    @Enumerated(EnumType.STRING)
    @Column(name = "status", length = 20, nullable = false)
    private VoucherStatusEnum status;

    @Enumerated(EnumType.STRING)
    @Column(name = "type", length = 20, nullable = false)
    private VoucherTypeEnum type;
    
    @Column(name = "created_at", nullable = false, updatable = false)
    private LocalDateTime createdAt;

    @Column(name = "updated_at", nullable = false)
    private LocalDateTime updatedAt;

    @Column(name = "deleted_at", nullable = true)
    private LocalDateTime deletedAt;
}