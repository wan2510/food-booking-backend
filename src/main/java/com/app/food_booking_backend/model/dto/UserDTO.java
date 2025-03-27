package com.app.food_booking_backend.model.dto;

import java.time.LocalDate;
import java.time.LocalDateTime;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.Setter;
@Setter
@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserDTO {
    private String uuid;
    private String email;
    private String hashPassword;
    private String fullName;
    private String phone;
    private String status;
    private String avatarUrl;
    private String role;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
