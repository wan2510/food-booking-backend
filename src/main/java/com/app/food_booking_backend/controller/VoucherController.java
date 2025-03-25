package com.app.food_booking_backend.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.app.food_booking_backend.service.VoucherService;
import com.app.food_booking_backend.model.dto.VoucherDTO;
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

    @GetMapping("/getListVoucher")
    public List<VoucherDTO> getAllVouchers() {
        return voucherService.getVouchers();
    }

    @PostMapping("/addNewVoucher")
    public ResponseEntity<Voucher> addVoucher(@RequestBody VoucherDTO voucherDTO) {
        return ResponseEntity.ok(voucherService.addVoucher(voucherDTO));
    }

    @PutMapping("/updateVoucher")
    public ResponseEntity<?> updateVoucher(@RequestBody VoucherDTO newVoucherDTO) {
        try {
            return ResponseEntity.ok(voucherService.updateVoucher(newVoucherDTO));
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(Map.of("error", e.getMessage()));
        }
    }
}