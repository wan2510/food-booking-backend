package com.app.food_booking_backend.repository;

import com.app.food_booking_backend.model.entity.Feedback;
import com.app.food_booking_backend.model.entity.enums.FeedbackTypeEnum.FeedbackType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface FeedbackRepository extends JpaRepository<Feedback, String> {

    List<Feedback> findByIsDeleteFalse();

    List<Feedback> findByRatingAndIsDeleteFalse(Integer rating);

    List<Feedback> findByTypeAndIsDeleteFalse(FeedbackType type);

    List<Feedback> findByIsRespondedAndIsDeleteFalse(Boolean isResponded);

    List<Feedback> findByRatingAndTypeAndIsDeleteFalse(Integer rating, FeedbackType type);

    List<Feedback> findByRatingAndIsRespondedAndIsDeleteFalse(Integer rating, Boolean isResponded);

    List<Feedback> findByTypeAndIsRespondedAndIsDeleteFalse(FeedbackType type, Boolean isResponded);

    List<Feedback> findByRatingAndTypeAndIsRespondedAndIsDeleteFalse(Integer rating, FeedbackType type, Boolean isResponded);
}