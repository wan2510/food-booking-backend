package com.app.food_booking_backend.model.response;

import com.app.food_booking_backend.model.dto.UserDTO;

import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class LoginResponse {
    private UserDTO user;
    private String accessToken;
}
