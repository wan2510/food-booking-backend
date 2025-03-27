package com.app.food_booking_backend.model.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Entity
@Table(name = "notifications")
@Getter
@Setter
@NoArgsConstructor
public class Notification {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long uuid;

    private Long userId;
    private String type;
    private String message;
    private boolean isRead;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private Long relatedId;
    private String priority;
    private String actionUrl;
    private String title;

    // Constructors
    public Notification(Long userId, String type, String message, String priority, String actionUrl, String title, Long relatedId) {
        this.userId = userId;
        this.type = type;
        this.message = message;
        this.priority = priority;
        this.actionUrl = actionUrl;
        this.title = title;
        this.relatedId = relatedId;
        this.isRead = false;
        this.createdAt = LocalDateTime.now();
        this.updatedAt = LocalDateTime.now();
    }
}
