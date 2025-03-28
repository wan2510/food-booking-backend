package com.app.food_booking_backend.model.dto;

import lombok.Data;

@Data
public class ShiftDTO {
    private String name;
    private String date;
    private String startTime;
    private String endTime;
    private String staffId;
    private String status;
    private String note;
}