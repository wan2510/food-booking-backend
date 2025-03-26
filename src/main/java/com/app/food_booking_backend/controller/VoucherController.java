package com.app.food_booking_backend.controller;

import com.app.food_booking_backend.model.dto.VoucherDTO;
import com.app.food_booking_backend.service.VoucherService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/vouchers")
public class VoucherController {

    @Autowired
    private VoucherService voucherService;

    @GetMapping
    public ResponseEntity<List<VoucherDTO>> getAllVouchers() {
        List<VoucherDTO> vouchers = voucherService.getAllVouchers();
        return ResponseEntity.ok(vouchers);
    }

    @GetMapping("/{uuid}")
    public ResponseEntity<VoucherDTO> getVoucherByUuid(@PathVariable String uuid) {
        VoucherDTO voucher = voucherService.getVoucherByUuid(uuid);
        return ResponseEntity.ok(voucher);
    }

    @PostMapping
    public ResponseEntity<VoucherDTO> createVoucher(@RequestBody VoucherDTO voucherDTO) {
        VoucherDTO createdVoucher = voucherService.createVoucher(voucherDTO);
        return ResponseEntity.ok(createdVoucher);
    }

    @PutMapping("/{uuid}")
    public ResponseEntity<VoucherDTO> updateVoucher(@PathVariable String uuid, @RequestBody VoucherDTO voucherDTO) {
        VoucherDTO updatedVoucher = voucherService.updateVoucher(uuid, voucherDTO);
        return ResponseEntity.ok(updatedVoucher);
    }

    @DeleteMapping("/{uuid}")
    public ResponseEntity<Void> deleteVoucher(@PathVariable String uuid) {
        voucherService.deleteVoucher(uuid);
        return ResponseEntity.ok().build();
    }
}