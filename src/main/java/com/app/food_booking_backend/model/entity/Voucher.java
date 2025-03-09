package com.app.food_booking_backend.model.entity;

import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Voucher {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Double discount; // Giá trị giảm giá (có thể là % hoặc tiền mặt)
    private Double maxDiscountValue; // Giảm tối đa nếu giảm theo %
    private Double minOrderValue; // Giá trị đơn hàng tối thiểu để dùng voucher
    private LocalDateTime expiredAt; // Ngày hết hạn
    private Integer remainQuantity; // Số lượng còn lại
    private LocalDateTime createdAt; // Ngày tạo voucher
}
