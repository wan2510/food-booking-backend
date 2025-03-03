package com.app.food_booking_backend.model.dto;

import com.app.food_booking_backend.model.entity.Role;
import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class UserDTO {
    private String id;
    private String email;
    private String fullName;
    private String phone;
    private String avatarUrl;
    private Role role;
}
