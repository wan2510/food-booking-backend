package com.app.food_booking_backend.model.dto;

import com.app.food_booking_backend.model.entity.Role;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserDTO {
    private String uuid;
    private String email;
    private String fullName;
    private String phone;
    private String avatarUrl;
    private String role;
}
