package com.app.food_booking_backend.model.entity;

import java.time.LocalDateTime;
import java.util.List;

import org.hibernate.annotations.CreationTimestamp;

import com.fasterxml.jackson.annotation.JsonManagedReference;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

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
    private String uuid;

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
    @JsonManagedReference
    private List<User> users;
}
