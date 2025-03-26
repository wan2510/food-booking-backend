package com.app.food_booking_backend.model.entity;

import jakarta.persistence.*;
import lombok.*;

import org.hibernate.annotations.CreationTimestamp;

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

    @Column(name = "name", length = 255, nullable = false)
    private String name;

    @Column(name = "code", length = 50, nullable = false, unique = true)
    private String code;

    @Column(name = "discount", nullable = false)
    private int discount;

    @Column(name = "max_discount_value", nullable = false)
    private int maxDiscountValue;

    @Column(name = "min_order_value", nullable = false)
    private int minOrderValue;

    @Column(name = "expired_at", nullable = false)
    private LocalDateTime expiredAt;

    @Enumerated(EnumType.STRING)
    @Column(name = "status", length = 20, nullable = false)
    private VoucherStatusEnum status;

    @Enumerated(EnumType.STRING)
    @Column(name = "type", length = 20, nullable = false)
    private VoucherTypeEnum type;
    
    @CreationTimestamp
    @Column(name = "created_at", nullable = false, updatable = false)
    private LocalDateTime createdAt;

    @CreationTimestamp
    @Column(name = "updated_at", nullable = false)
    private LocalDateTime updatedAt;
}
