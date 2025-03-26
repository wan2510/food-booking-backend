package com.app.food_booking_backend.model.dto;

import java.time.LocalDateTime;
import lombok.*;

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