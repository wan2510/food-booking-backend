package com.app.food_booking_backend.model.dto;

import lombok.Data;
import java.time.LocalDate;
import java.time.LocalTime;

@Data
public class ShiftResponseDTO {
    private Long uuid; // Sửa từ String thành Long
    private String name;
    private LocalDate date;
    private LocalTime startTime;
    private LocalTime endTime;
    private String staffId;
    private String staffName;
    private String status;
    private String note;
}