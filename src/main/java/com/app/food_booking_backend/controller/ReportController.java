package com.app.food_booking_backend.controller;

import com.app.food_booking_backend.model.dto.RevenueReportDTO;
import com.app.food_booking_backend.model.dto.RevenueRequestDTO;
import com.app.food_booking_backend.service.ReportService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/reports")
@RequiredArgsConstructor
public class ReportController {
    private final ReportService reportService;

    @GetMapping("/revenue")
    public ResponseEntity<RevenueReportDTO> getRevenueReport(@Valid RevenueRequestDTO request) {
        RevenueReportDTO report = reportService.generateRevenueReport(request);
        return ResponseEntity.ok(report);
    }
}
