package com.app.food_booking_backend.model.request;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Getter
public class LoginRequest {
    private String email;
    private String password;
}
