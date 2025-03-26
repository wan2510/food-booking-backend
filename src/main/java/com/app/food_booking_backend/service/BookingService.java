package com.app.food_booking_backend.service;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.UUID;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.app.food_booking_backend.model.entity.Booking;
import com.app.food_booking_backend.model.entity.User;
import com.app.food_booking_backend.model.entity.enums.BookingStatus;
import com.app.food_booking_backend.repository.BookingRepository;
import com.app.food_booking_backend.repository.UserRepository;

@Service
public class BookingService {

    private final BookingRepository bookingRepository;
    private final UserRepository userRepository;

    public BookingService(BookingRepository bookingRepository, UserRepository userRepository) {
        this.bookingRepository = bookingRepository;
        this.userRepository = userRepository;
    }

    @Transactional
    public Booking createBooking(String userId, int guests, LocalDate date, LocalTime time) {
        if (date.isBefore(LocalDate.now())) {
            throw new IllegalArgumentException("Ngày đặt bàn không được ở quá khứ");
        }

        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User không tồn tại"));

        Booking booking = Booking.builder()
                .uuid(UUID.randomUUID().toString())
                .user(user)
                .guests(guests)
                .date(date)
                .time(time)
                .status(BookingStatus.PENDING)
                .build();

        return bookingRepository.save(booking);
    }
}
