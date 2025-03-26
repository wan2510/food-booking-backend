package com.app.food_booking_backend.model.entity;

import java.time.LocalDate;
import java.time.LocalDateTime;

import org.hibernate.annotations.CreationTimestamp;

import jakarta.persistence.*;
import lombok.*;
import com.app.food_booking_backend.model.entity.enums.UserStatusEnum;
import com.fasterxml.jackson.annotation.JsonManagedReference;

@Entity
@Table(name = "user")
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(length = 36, nullable = false, unique = true)
    private String uuid;

    @Column(nullable = false, unique = true)
    private String email;

    @Column(length = 255, nullable = false)
    private String hashPassword;

    @Column(length = 255, nullable = false)
    private String fullName;

    @Column(length = 15, nullable = false, unique = true)
    private String phone;

    @Enumerated(EnumType.STRING)
    private UserStatusEnum status;

    @Column(length = 255)
    private String avatarUrl;

    @ManyToOne
    @JoinColumn(name = "role_id", nullable = false)
    @JsonManagedReference
    private Role role;

    @OneToOne(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
    private Cart cart;

    @CreationTimestamp
    @Column(name = "created_at", nullable = false, updatable = false)
    private LocalDateTime createdAt;

    @CreationTimestamp
    @Column(name = "updated_at", nullable = false)
    @Column(name = "updated_at", nullable = false)
    private LocalDateTime updatedAt;

    // Thêm hai trường mới
    @Column(name = "start_date")
    private LocalDate startDate;

    @Column(name = "note")
    private String note;
}