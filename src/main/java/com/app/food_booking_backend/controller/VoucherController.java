package com.app.food_booking_backend.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.app.food_booking_backend.service.VoucherService;
import com.app.food_booking_backend.model.entity.Voucher;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/voucher")
@CrossOrigin(origins = "http://localhost:5173", allowCredentials = "true")
public class VoucherController {
    private final VoucherService voucherService;

    public VoucherController(VoucherService voucherService) {
        this.voucherService = voucherService;
    }

    @GetMapping("/getVouchers")
    public List<Voucher> getAllVouchers() {
        return voucherService.getVouchers();
    }

    @PostMapping("/addVoucher")
    public ResponseEntity<Voucher> addVoucher(@RequestBody Voucher voucher) {
        return ResponseEntity.ok(voucherService.addVoucher(voucher));
    }

    @PutMapping("/updateVoucher/{id}")
    public ResponseEntity<?> updateVoucher(@PathVariable String id, @RequestBody Voucher newVoucher) {
        try {
            return ResponseEntity.ok(voucherService.updateVoucher(id, newVoucher));
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(Map.of("error", e.getMessage()));
        }
    }
}