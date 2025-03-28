package com.app.food_booking_backend.model.entity;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

import org.hibernate.annotations.CreationTimestamp;
import com.fasterxml.jackson.annotation.JsonFormat;

@Entity
@Table(name = "combos") // Matches the expected table name
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Combo {

    @Id
    @Column(length = 36, nullable = false, unique = true)
    private String uuid; // Changed to String to align with Food

    @Column(length = 255, nullable = false)
    private String name;

    @Column(precision = 10, scale = 2, nullable = false)
    private BigDecimal totalPrice; // Changed to BigDecimal for consistency with Food

    @Column(columnDefinition = "TEXT")
    private String description;

    @Column(length = 255)
    private String imageUrl; // Renamed to match Food's naming convention

    @Column(length = 50, nullable = false)
    private String status;

    @ManyToMany
    @JoinTable(
        name = "combo_food",
        joinColumns = @JoinColumn(name = "combo_uuid"), // Updated to reference uuid
        inverseJoinColumns = @JoinColumn(name = "food_uuid") // Updated to reference uuid
    )
    @JsonManagedReference
    private List<Food> foods;

    @CreationTimestamp
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss")
    @Column(name = "created_at", nullable = false)
    private LocalDateTime createdAt;

    @CreationTimestamp
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss")
    @Column(name = "updated_at", nullable = false)
    private LocalDateTime updatedAt;
}