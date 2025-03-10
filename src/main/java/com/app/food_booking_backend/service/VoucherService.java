package com.app.food_booking_backend.service;

import com.app.food_booking_backend.model.entity.Voucher;
import com.app.food_booking_backend.model.entity.enums.VoucherStatusEnum;
import com.app.food_booking_backend.model.entity.enums.VoucherTypeEnum;
import com.app.food_booking_backend.repository.VoucherRepository;

import jakarta.transaction.Transactional;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class VoucherService {

    private final VoucherRepository voucherRepository;

    public VoucherService(VoucherRepository voucherRepository) {
        this.voucherRepository = voucherRepository;
    }
//  get / search voucher(s)
    public List<Voucher> getVouchers() {
        return voucherRepository.findByNameContainingIgnoreCase("");
    }

    private Optional<Voucher> getVoucherById(String id) {
        return voucherRepository.findById(id);
    }

//  update / add
    public Voucher addVoucher(Voucher voucher) {
        Long newId = voucherRepository.findMaxId()
                                      .map(Long::parseLong)
                                      .orElse(0L) + 1;
    
        voucher.setId(String.valueOf(newId));
        return voucherRepository.save(voucher);
    }
    
    public Voucher updateVoucher(String id, Voucher newVoucher) {
        Optional<Voucher> existingVoucher = getVoucherById(id);
        if (existingVoucher.isPresent()) {
            Voucher voucher = existingVoucher.get();
            voucher.setName(newVoucher.getName());
            voucher.setDiscount(newVoucher.getDiscount());
            voucher.setMaxDiscountValue(newVoucher.getMaxDiscountValue());
            voucher.setMinOrderValue(newVoucher.getMinOrderValue());
            voucher.setType(newVoucher.getType());
            voucher.setStatus(newVoucher.getStatus());
            return voucherRepository.save(voucher);
        } else {
            throw new RuntimeException("Voucher không tồn tại!");
        }
    }

    @Scheduled(cron = "0 0 0 * * ?") 
    @Transactional
    public void updateExpiredVouchers() {
        List<Voucher> expiredVouchers = voucherRepository.findExpiredVouchers(LocalDateTime.now());

        for (Voucher voucher : expiredVouchers) {
            voucher.setStatus(VoucherStatusEnum.KHÔNG_KHẢ_DỤNG);
            voucherRepository.save(voucher);
        }

        System.out.println("Updated " + expiredVouchers.size() + " expired vouchers.");
    }

    public List<Voucher> getVouchersByType(VoucherTypeEnum type) {
    return voucherRepository.findByType(type);
    }

}
