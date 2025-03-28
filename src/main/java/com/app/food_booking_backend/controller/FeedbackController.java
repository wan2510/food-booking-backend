package com.app.food_booking_backend.controller;

import com.app.food_booking_backend.model.entity.Feedback;
import com.app.food_booking_backend.model.entity.enums.FeedbackTypeEnum.FeedbackType;
import com.app.food_booking_backend.service.FeedbackService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/feedbacks")
@CrossOrigin(origins = "http://localhost:5173") // Điều chỉnh theo URL frontend của bạn
public class FeedbackController {

    @Autowired
    private FeedbackService feedbackService;

    @GetMapping
    public List<Feedback> getFeedbacks(
            @RequestParam(name = "rating", required = false) Integer rating,
            @RequestParam(name = "type", required = false) String type, // Nhận type dưới dạng chuỗi
            @RequestParam(name = "isResponded", required = false) Boolean isResponded) {
        // Chuyển đổi chuỗi type thành FeedbackType
        FeedbackType feedbackType = null;
        if (type != null) {
            try {
                feedbackType = FeedbackType.valueOf(type);
            } catch (IllegalArgumentException e) {
                throw new IllegalArgumentException("Loại feedback không hợp lệ: " + type);
            }
        }
        return feedbackService.getFilteredFeedbacks(rating, feedbackType, isResponded);
    }
    @GetMapping("/{uuid}")
    public ResponseEntity<Feedback> getFeedbackById(@PathVariable String uuid) {
        Feedback feedback = feedbackService.getFeedbackById(uuid);
        return ResponseEntity.ok(feedback);
    }

    @PostMapping("/{uuid}/respond")
    public ResponseEntity<Feedback> respondToFeedback(@PathVariable("uuid") String uuid, @RequestBody String responseContent) {
        Feedback updatedFeedback = feedbackService.respondToFeedback(uuid, responseContent);
        return ResponseEntity.ok(updatedFeedback);
    }

    @PutMapping("/{uuid}/hide")
    public ResponseEntity<Feedback> hideFeedback(@PathVariable("uuid") String uuid) {
        Feedback updatedFeedback = feedbackService.hideFeedback(uuid);
        return ResponseEntity.ok(updatedFeedback);
    }
}