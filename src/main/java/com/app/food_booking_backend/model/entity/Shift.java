package com.app.food_booking_backend.model.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.LocalDateTime;

@Entity
@Table(name = "shift")
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Shift {

    @Id
    @Column(length = 36, nullable = false)
    private String uuid;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private LocalDate date;

    @Column(name = "start_time", nullable = false)
    private LocalTime startTime;

    @Column(name = "end_time", nullable = false)
    private LocalTime endTime;

    @ManyToOne
    @JoinColumn(name = "staff_id", nullable = false)
    private User staff;

    private String status;

    @Column(name = "created_at", nullable = false)
    private LocalDateTime createdAt;

    @Column(name = "updated_at", nullable = false)
    private LocalDateTime updatedAt;
}