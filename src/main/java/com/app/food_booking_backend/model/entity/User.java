package com.app.food_booking_backend.model.entity;

import java.time.LocalDateTime;

import org.hibernate.annotations.CreationTimestamp;

import com.app.food_booking_backend.model.entity.enums.UserStatusEnum;
import com.fasterxml.jackson.annotation.JsonManagedReference;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity(name = "user")
@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(
            name = "uuid",
            length = 36,
            nullable = false,
            unique = true
    )
    private String uuid;

    @Column(
            name = "email",
            nullable = false,
            unique = true
    )
    private String email;

    @Column(
            name = "hash_password",
            length = 255,
            nullable = false
    )
    private String hashPassword;

    @Column(
            name = "full_name",
            length = 255,
            nullable = false
    )
    private String fullName;

    @Column(
            name = "phone",
            length = 15,
            nullable = false,
            unique = true
    )
    private String phone;

    @Column(
            name = "status"
    )
    @Enumerated(EnumType.STRING)
    private UserStatusEnum status;

    @Column(
            name = "avatar_url",
            length = 255
    )
    private String avatarUrl;

    @ManyToOne
    @JoinColumn(name="role_id", nullable=false)
    @JsonManagedReference
    private Role role;

    @CreationTimestamp
    @Column(name = "created_at", nullable = false)
    private LocalDateTime createdAt;

    @CreationTimestamp
    @Column(name = "update_at", nullable = false)
    private LocalDateTime updatedAt;
}
