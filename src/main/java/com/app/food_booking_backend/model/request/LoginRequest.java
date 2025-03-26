package com.app.food_booking_backend.model.request;

import lombok.Getter;
import lombok.NoArgsConstructor;


@NoArgsConstructor
@Getter
public class LoginRequest {
    private String email;
    private String password;
}
