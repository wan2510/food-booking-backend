package com.app.food_booking_backend.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import com.app.food_booking_backend.model.entity.*;
import com.app.food_booking_backend.model.entity.enums.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Repository
public interface VoucherRepository extends JpaRepository<Voucher, String> {
    
    List<Voucher> findByNameContainingIgnoreCase(String name);

    @Query("SELECT MAX(v.id) FROM Voucher v WHERE v.id REGEXP '^[0-9]+$'")
    Optional<String> findMaxId();

    List<Voucher> findByType(VoucherTypeEnum type);

    @Query("SELECT v FROM voucher v WHERE v.expiredAt <= :now AND v.status = 'KHẢ_DỤNG'")
    List<Voucher> findExpiredVouchers(@Param("now") LocalDateTime now);

}
