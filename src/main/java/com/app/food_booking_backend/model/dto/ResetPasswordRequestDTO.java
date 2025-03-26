package com.app.food_booking_backend.model.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class ResetPasswordRequestDTO {
    private String email;
    private String otp;
    private String newPassword;
}