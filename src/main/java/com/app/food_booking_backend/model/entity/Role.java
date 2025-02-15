package com.app.food_booking_backend.model.entity;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;
import java.util.List;

@Entity(name = "role")
@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Role {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(
            name = "uuid",
            length = 36,
            nullable = false,
            unique = true
    )
    private String id;

    @Column(
            name = "name",
            length = 255,
            nullable = false,
            unique = true
    )
    private String name;

    @Column(
            name = "description",
            length = 255
    )
    private String description;

    @CreationTimestamp
    @Column(name = "created_at", nullable = false)
    private LocalDateTime createdAt;

    @CreationTimestamp
    @Column(name = "update_at", nullable = false)
    private LocalDateTime updatedAt;

    @OneToMany(mappedBy = "role")
    private List<User> users;
}
