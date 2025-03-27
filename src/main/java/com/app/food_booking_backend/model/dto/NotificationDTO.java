package com.app.food_booking_backend.model.dto;

import java.time.LocalDateTime;

public class NotificationDTO {

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

    // Constructor đầy đủ
    public NotificationDTO(Long uuid, Long userId, String type, String message, boolean isRead, 
                           LocalDateTime createdAt, LocalDateTime updatedAt, Long relatedId, 
                           String priority, String actionUrl, String title) {
        this.uuid = uuid;
        this.userId = userId;
        this.type = type;
        this.message = message;
        this.isRead = isRead;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
        this.relatedId = relatedId;
        this.priority = priority;
        this.actionUrl = actionUrl;
        this.title = title;
    }

    // Getters và Setters
    public Long getUuid() {
        return uuid;
    }

    public void setUuid(Long uuid) {
        this.uuid = uuid;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public boolean isRead() {
        return isRead;
    }

    public void setRead(boolean read) {
        isRead = read;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public LocalDateTime getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(LocalDateTime updatedAt) {
        this.updatedAt = updatedAt;
    }

    public Long getRelatedId() {
        return relatedId;
    }

    public void setRelatedId(Long relatedId) {
        this.relatedId = relatedId;
    }

    public String getPriority() {
        return priority;
    }

    public void setPriority(String priority) {
        this.priority = priority;
    }

    public String getActionUrl() {
        return actionUrl;
    }

    public void setActionUrl(String actionUrl) {
        this.actionUrl = actionUrl;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }
}
