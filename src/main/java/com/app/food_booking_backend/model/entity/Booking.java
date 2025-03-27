package com.app.food_booking_backend.model.entity;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

import org.hibernate.annotations.CreationTimestamp;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "booking")
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Booking {

    @Id
    @Column(length = 36, nullable = false, unique = true)
    private String uuid;

    @Column(length = 36, nullable = false)
    private String userUuid;

    @Column(length = 255, nullable = false)
    private String name;

    @Column(length = 20, nullable = false)
    private String phone;

    @Column(nullable = false)
    private int guests;

    @Column(nullable = false)
    private LocalDate date;

    @Column(nullable = false)
    private LocalTime time;

    @Column(columnDefinition = "TEXT")
    private String note;

    @Column(name = "table_type", length = 50, nullable = false)
    private String tableType;

    @Column(length = 255)
    private String occasion;

    @Column(length = 50, nullable = false)
    private String status;

    @ManyToOne
    @JoinColumn(name = "table_id")
    private TableRestaurant table;

    @CreationTimestamp
    @Column(name = "created_at", nullable = false, updatable = false)
    private LocalDateTime createdAt;

    @CreationTimestamp
    @Column(name = "updated_at", nullable = false)
    private LocalDateTime updatedAt;
}
