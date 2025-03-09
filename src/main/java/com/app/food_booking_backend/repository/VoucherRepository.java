package com.app.food_booking_backend.repository;

import com.app.food_booking_backend.model.entity.Voucher;
import org.springframework.data.jpa.repository.JpaRepository;
import java.time.LocalDateTime;
import java.util.List;

public interface VoucherRepository extends JpaRepository<Voucher, Long> {
    List<Voucher> findByExpiredAtAfterAndRemainQuantityGreaterThan(LocalDateTime now, int quantity);
}
