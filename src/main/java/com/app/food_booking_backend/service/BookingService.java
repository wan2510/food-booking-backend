package com.app.food_booking_backend.service;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.UUID;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.app.food_booking_backend.model.entity.Booking;
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
    public Booking createBooking(String userUuid,
                                 String name,
                                 String phone,
                                 int guests,
                                 LocalDate date,
                                 LocalTime time,
                                 String note,
                                 String tableType,
                                 String occasion) {

        // Kiểm tra ngày đặt bàn
        if (date.isBefore(LocalDate.now())) {
            throw new IllegalArgumentException("Ngày đặt bàn không được ở quá khứ");
        }

        // Kiểm tra user có tồn tại không
        userRepository.findById(userUuid)
                .orElseThrow(() -> new RuntimeException("User không tồn tại"));

        // Tạo booking mới
        Booking booking = Booking.builder()
                .uuid(UUID.randomUUID().toString())
                .userUuid(userUuid)
                .name(name)
                .phone(phone)
                .guests(guests)
                .date(date)
                .time(time)
                .note(note)
                .tableType(tableType)
                .occasion(occasion)
                .status(BookingStatus.PENDING.toString())
                .build();

        return bookingRepository.save(booking);
    }
}
