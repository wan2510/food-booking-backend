package com.app.food_booking_backend.controller;

import com.app.food_booking_backend.model.dto.NotificationDTO;
import com.app.food_booking_backend.service.NotificationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/notifications")
public class NotificationController {

    private final NotificationService notificationService;

    @Autowired
    public NotificationController(NotificationService notificationService) {
        this.notificationService = notificationService;
    }

    @GetMapping
    public ResponseEntity<List<NotificationDTO>> getAllNotifications() {
        List<NotificationDTO> notifications = notificationService.getAllNotifications();
        return ResponseEntity.ok(notifications);
    }

    @GetMapping("/{id}")
    public ResponseEntity<NotificationDTO> getNotificationById(@PathVariable("id") Long id) { // Đổi uuid thành id
        NotificationDTO notification = notificationService.getNotificationByUuid(id);
        return ResponseEntity.ok(notification);
    }

    @GetMapping("/user/{userId}")
    public ResponseEntity<List<NotificationDTO>> getNotificationsByUserId(@PathVariable("userId") Long userId) {
        List<NotificationDTO> notifications = notificationService.getNotificationsByUserId(userId);
        return ResponseEntity.ok(notifications);
    }

    @GetMapping("/{userId}/unread")
    public ResponseEntity<List<NotificationDTO>> getUnreadNotificationsByUserId(@PathVariable("userId") Long userId) {
        List<NotificationDTO> unreadNotifications = notificationService.getUnreadNotificationsByUserId(userId);
        return ResponseEntity.ok(unreadNotifications);
    }

    @PutMapping("/{id}/read")
    public ResponseEntity<?> markAsRead(@PathVariable("id") String id) {
    try {
        Long notificationId = Long.parseLong(id); 
        if (notificationId <= 0) {
            return ResponseEntity.badRequest().body("Invalid notification ID");
        }

        NotificationDTO notification = notificationService.markAsRead(notificationId);
        return ResponseEntity.ok(notification);
    } catch (NumberFormatException e) {
        return ResponseEntity.badRequest().body("Invalid notification ID format: " + id);
    }
    }

    /**
     * Xóa thông báo
     *
     * @param id ID của thông báo
     * @return Trạng thái xóa
     */
@DeleteMapping("/{id}")
public ResponseEntity<?> deleteNotification(@PathVariable("id") String id) {
    try {
        Long notificationId = Long.parseLong(id);
        if (notificationId <= 0) {
            return ResponseEntity.badRequest().body("Invalid notification ID");
        }

        notificationService.deleteNotification(notificationId);
        return ResponseEntity.ok().body("Notification deleted successfully");
    } catch (NumberFormatException e) {
        return ResponseEntity.badRequest().body("Invalid notification ID format: " + id);
    }
}


}