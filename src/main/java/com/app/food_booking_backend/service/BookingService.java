package com.app.food_booking_backend.service;


import com.app.food_booking_backend.model.dto.BookingDTO;
import com.app.food_booking_backend.model.entity.Booking;
import com.app.food_booking_backend.model.entity.User;
import com.app.food_booking_backend.repository.BookingRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.UUID;

@Service
public class BookingService {

    @Autowired
    private BookingRepository bookingRepository;

    @Autowired
    private NotificationService notificationService;

    @Autowired
    private UserService userService;

    public BookingDTO createBooking(BookingDTO bookingDTO) {
        User user = userService.findByUuid(bookingDTO.getUserId());
        if (user == null) {
            throw new RuntimeException("User not found with ID: " + bookingDTO.getUserId());
        }

        Booking booking = Booking.builder()
                .uuid(UUID.randomUUID().toString())
                .user(user)
                .guests(bookingDTO.getGuests())
                .date(bookingDTO.getDate())
                .time(bookingDTO.getTime())
                .status("CONFIRMED")
                .createdAt(LocalDateTime.now())
                .updatedAt(LocalDateTime.now())
                .build();

        bookingRepository.save(booking);

        String adminId = "550e8400-e29b-41d4-a716-446655440000";
        notificationService.createAndSendNotification(
                adminId,
                "BOOKING_SUCCESS",
                "A new booking has been made successfully! Booking ID: " + booking.getUuid()
        );

        return convertToDTO(booking);
    }

    private BookingDTO convertToDTO(Booking booking) {
        BookingDTO dto = new BookingDTO();
        dto.setUuid(booking.getUuid());
        dto.setUserId(booking.getUser().getUuid());
        dto.setGuests(booking.getGuests());
        dto.setDate(booking.getDate());
        dto.setTime(booking.getTime());
        dto.setStatus(booking.getStatus());
        dto.setCreatedAt(booking.getCreatedAt());
        dto.setUpdatedAt(booking.getUpdatedAt());
        return dto;
    }
}