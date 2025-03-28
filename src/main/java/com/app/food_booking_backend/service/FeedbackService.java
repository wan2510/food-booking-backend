package com.app.food_booking_backend.service;

import com.app.food_booking_backend.model.entity.Feedback;
import com.app.food_booking_backend.model.entity.enums.FeedbackTypeEnum.FeedbackType;
import com.app.food_booking_backend.repository.FeedbackRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class FeedbackService {

    @Autowired
    private FeedbackRepository feedbackRepository;

    public List<Feedback> getFilteredFeedbacks(Integer rating, FeedbackType type, Boolean isResponded) {
        // Chỉ lấy các feedback chưa bị xóa
        if (rating != null && type != null && isResponded != null) {
            return feedbackRepository.findByRatingAndTypeAndIsRespondedAndIsDeleteFalse(rating, type, isResponded);
        } else if (rating != null && type != null) {
            return feedbackRepository.findByRatingAndTypeAndIsDeleteFalse(rating, type);
        } else if (rating != null && isResponded != null) {
            return feedbackRepository.findByRatingAndIsRespondedAndIsDeleteFalse(rating, isResponded);
        } else if (type != null && isResponded != null) {
            return feedbackRepository.findByTypeAndIsRespondedAndIsDeleteFalse(type, isResponded);
        } else if (rating != null) {
            return feedbackRepository.findByRatingAndIsDeleteFalse(rating);
        } else if (type != null) {
            return feedbackRepository.findByTypeAndIsDeleteFalse(type);
        } else if (isResponded != null) {
            return feedbackRepository.findByIsRespondedAndIsDeleteFalse(isResponded);
        }
        return feedbackRepository.findByIsDeleteFalse();
    }

    public Feedback getFeedbackById(String uuid) {
        Optional<Feedback> feedback = feedbackRepository.findById(uuid);
        return feedback.orElseThrow(() -> new RuntimeException("Không tìm thấy feedback với uuid: " + uuid));
    }

    public Feedback respondToFeedback(String uuid, String responseContent) {
        Feedback feedback = getFeedbackById(uuid);
        feedback.setIsResponded(true);
        feedback.setResponseContent(responseContent);
        return feedbackRepository.save(feedback);
    }

    public Feedback hideFeedback(String uuid) {
        Feedback feedback = getFeedbackById(uuid);
        feedback.setIsDelete(true);
        return feedbackRepository.save(feedback);
    }
}