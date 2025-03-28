package com.app.food_booking_backend.model.entity;

import com.app.food_booking_backend.model.entity.enums.FeedbackTypeEnum.FeedbackType;
import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "feedbacks")
public class Feedback {

    @Id
    @Column(name = "uuid")
    private String uuid;

    @Column(name = "user_id", nullable = false)
    private String userId;

    @Column(nullable = false)
    private String content;

    @Column(nullable = false)
    private Integer rating;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private FeedbackType type;

    @Column(name = "is_responded", nullable = false)
    private Boolean isResponded;

    @Column(name = "is_delete", nullable = false)
    private Boolean isDelete;

    @Column(name = "created_at", nullable = false)
    private LocalDateTime createdAt;

    @Column(name = "response_content") // Thêm trường mới để lưu nội dung phản hồi
    private String responseContent;

    // Getters và Setters
    public String getUuid() {
        return uuid;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Integer getRating() {
        return rating;
    }

    public void setRating(Integer rating) {
        this.rating = rating;
    }

    public FeedbackType getType() {
        return type;
    }

    public void setType(FeedbackType type) {
        this.type = type;
    }

    public Boolean getIsResponded() {
        return isResponded;
    }

    public void setIsResponded(Boolean isResponded) {
        this.isResponded = isResponded;
    }

    public Boolean getIsDelete() {
        return isDelete;
    }

    public void setIsDelete(Boolean isDelete) {
        this.isDelete = isDelete;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public String getResponseContent() {
        return responseContent;
    }

    public void setResponseContent(String responseContent) {
        this.responseContent = responseContent;
    }
}