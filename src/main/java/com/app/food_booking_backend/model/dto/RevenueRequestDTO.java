package com.app.food_booking_backend.model.dto;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.time.LocalDate;

@Data
public class RevenueRequestDTO {
    @NotNull(message = "Ngày bắt đầu không được để trống")
    private LocalDate startDate;

    @NotNull(message = "Ngày kết thúc không được để trống")
    private LocalDate endDate;

    private String timeGroup = "day"; // day, month, year
    private String status = "all"; // COMPLETED, PENDING, CANCELLED, all
}
