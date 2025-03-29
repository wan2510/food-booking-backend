package com.app.food_booking_backend.model.dto;

import java.time.LocalDate;
import java.time.LocalTime;

import com.app.food_booking_backend.model.entity.enums.BookingStatus;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class BookingDTO {
    private String uuid;
    private String userUuid;
    private String name;
    private String phone;
    private int guests;
    private LocalDate date;
    private LocalTime time;
    private String note;
    private String tableType;
    private String occasion;
    private BookingStatus status;
}
