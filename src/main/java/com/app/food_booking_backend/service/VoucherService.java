package com.app.food_booking_backend.service;

import com.app.food_booking_backend.model.entity.Voucher;
import com.app.food_booking_backend.repository.VoucherRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class VoucherService {
    private final VoucherRepository voucherRepository;

    // Lấy danh sách voucher hợp lệ
    public List<Voucher> getAvailableVouchers() {
        return voucherRepository.findByExpiredAtAfterAndRemainQuantityGreaterThan(LocalDateTime.now(), 0);
    }

    // Thêm voucher mới
    public void createVoucher(Voucher voucher) {
        voucher.setCreatedAt(LocalDateTime.now());
        voucherRepository.save(voucher);
    }
}
