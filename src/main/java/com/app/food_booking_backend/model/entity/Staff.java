package com.app.food_booking_backend.model.entity;

import jakarta.persistence.*;
import lombok.Data;
import java.time.LocalDateTime;

@Entity
@Table(name = "staff")
@Data
public class Staff {
    @Id
    @Column(length = 36)
    private String id;

    @Column(nullable = false)
    private String name;

    private String email;

    private String phone;

    @Column(name = "created_at")
    private LocalDateTime createdAt;

    @Column(name = "updated_at")
    private LocalDateTime updatedAt;
}
