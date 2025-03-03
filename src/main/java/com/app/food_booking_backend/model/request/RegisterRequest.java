package com.app.food_booking_backend.model.request;

import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Getter
public class RegisterRequest {
    private String email;
    private String password;
    private String fullName;
    private String phone;
}
