package com.app.food_booking_backend.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.app.food_booking_backend.model.entity.Voucher;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface VoucherRepository extends JpaRepository<Voucher, String> {

    // Lấy danh sách voucher hết hạn
    @Query("SELECT v FROM Voucher v WHERE v.expiredAt <= :now")
    List<Voucher> findExpiredVouchers(@Param("now") LocalDateTime now);

    // Lấy danh sách voucher chưa bị xóa (deleted_at IS NULL)
    @Query("SELECT v FROM Voucher v WHERE v.deletedAt IS NULL")
    List<Voucher> findActiveVouchers();
}