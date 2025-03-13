package com.app.food_booking_backend.controller;

import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.app.food_booking_backend.model.dto.BookingDTO;
import com.app.food_booking_backend.model.entity.Booking;
import com.app.food_booking_backend.service.BookingService;

@RestController
@RequestMapping("/api/bookings")
public class BookingController {

    private final BookingService bookingService;
    private final ModelMapper modelMapper;

    public BookingController(BookingService bookingService, ModelMapper modelMapper) {
         this.bookingService = bookingService;
         this.modelMapper = modelMapper;
    }

    @PostMapping
    public ResponseEntity<BookingDTO> createBooking(@RequestBody BookingDTO bookingRequest) {
         String userId = (String) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
         Booking booking = bookingService.createBooking(
                userId,
                bookingRequest.getGuests(),
                bookingRequest.getDate(),
                bookingRequest.getTime()
         );

         BookingDTO response = modelMapper.map(booking, BookingDTO.class);
         return new ResponseEntity<>(response, HttpStatus.CREATED);
    }
}
