package com.app.food_booking_backend.model.entity;

import java.time.LocalDateTime;

import org.hibernate.annotations.CreationTimestamp;

import com.fasterxml.jackson.annotation.JsonFormat;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "table_restaurant")
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class TableRestaurant {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "table_number", length = 10, nullable = false, unique = true)
    private String tableNumber;

    @Column(nullable = false)
    private int capacity;

    @Column(name = "booked_guests", nullable = false)
    private int bookedGuests;

    @Column(length = 20, nullable = false)
    private String status;

    @Column(length = 50, nullable = false)
    private String type;

    @CreationTimestamp
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss")
    @Column(name = "created_at", nullable = false, updatable = false)
    private LocalDateTime createdAt;

    @CreationTimestamp
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss")
    @Column(name = "updated_at", nullable = false)
    private LocalDateTime updatedAt;
}
