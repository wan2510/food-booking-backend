package com.app.food_booking_backend.controller;

import com.app.food_booking_backend.model.entity.Voucher;
import com.app.food_booking_backend.service.VoucherService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/vouchers")
@RequiredArgsConstructor
public class VoucherController {
    private final VoucherService voucherService;

    @GetMapping("/available")
    public ResponseEntity<List<Voucher>> getAvailableVouchers() {
        return ResponseEntity.ok(voucherService.getAvailableVouchers());
    }

    @PostMapping("/create")
    public ResponseEntity<String> createVoucher(@RequestBody Voucher voucher) {
        voucherService.createVoucher(voucher);
        return ResponseEntity.ok("Voucher created successfully!");
    }
}
