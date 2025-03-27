package com.app.food_booking_backend.service;

import com.app.food_booking_backend.model.dto.NotificationDTO;
import com.app.food_booking_backend.model.entity.Notification;
import com.app.food_booking_backend.repository.NotificationRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@Service
public class NotificationService {

    private static final Logger logger = LoggerFactory.getLogger(NotificationService.class);
    private static final String NOT_FOUND_MESSAGE = "Không tìm thấy thông báo với ID: ";

    private final NotificationRepository notificationRepository;

    @Autowired
    public NotificationService(NotificationRepository notificationRepository) {
        this.notificationRepository = notificationRepository;
    }

    public List<NotificationDTO> getAllNotifications() {
        List<Notification> notifications = notificationRepository.findAll();
        return notifications.stream()
                .map(this::convertToDTO)
                .toList(); // Sử dụng toList() thay vì collect(Collectors.toList())
    }

    public NotificationDTO getNotificationByUuid(Long id) {
        Notification notification = notificationRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, NOT_FOUND_MESSAGE + id));
        return convertToDTO(notification);
    }

    public List<NotificationDTO> getNotificationsByUserId(Long userId) {
        List<Notification> notifications = notificationRepository.findByUserId(userId);
        return notifications.stream().map(this::convertToDTO).toList();
    }

    public List<NotificationDTO> getUnreadNotificationsByUserId(Long userId) {
        List<Notification> notifications = notificationRepository.findByUserIdAndIsReadFalse(userId);
        return notifications.stream().map(this::convertToDTO).toList();
    }

    private NotificationDTO convertToDTO(Notification notification) {
        return new NotificationDTO(
            notification.getUuid(),
            notification.getUserId(),
            notification.getType(),
            notification.getMessage(),
            notification.isRead(),
            notification.getCreatedAt(),
            notification.getUpdatedAt(),
            notification.getRelatedId(),
            notification.getPriority(),
            notification.getActionUrl(),
            notification.getTitle()
        );
    }

    public NotificationDTO markAsRead(Long id) {
        Notification notification = notificationRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, NOT_FOUND_MESSAGE + id));
        notification.setRead(true);
        Notification updatedNotification = notificationRepository.save(notification);
        return convertToDTO(updatedNotification);
    }

    public void deleteNotification(Long id) {
        Notification notification = notificationRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, NOT_FOUND_MESSAGE + id));
        notificationRepository.delete(notification);
    }
    
    public void createAndSendNotification(String recipientId, String notificationType, String message) {
        // Logic to create and send a notification
        logger.info("Notification sent to {} with type {}: {}", recipientId, notificationType, message);
    }
}