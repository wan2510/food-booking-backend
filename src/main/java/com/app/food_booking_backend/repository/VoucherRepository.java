package com.app.food_booking_backend.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import com.app.food_booking_backend.model.entity.Voucher;
import com.app.food_booking_backend.model.entity.enums.VoucherTypeEnum;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Repository
public interface VoucherRepository extends JpaRepository<Voucher, String> {

    @Query("SELECT MAX(CAST(v.id AS long)) FROM Voucher v")
    Optional<Long> findMaxId();

    List<Voucher> findByType(VoucherTypeEnum type);

    @Query("SELECT v FROM Voucher v WHERE v.expiredAt <= :now")
    List<Voucher> findExpiredVouchers(@Param("now") LocalDateTime now);
}
