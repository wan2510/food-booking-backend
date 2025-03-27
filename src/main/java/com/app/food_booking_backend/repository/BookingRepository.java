package com.app.food_booking_backend.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.app.food_booking_backend.model.entity.Booking;

public interface BookingRepository extends JpaRepository<Booking, String> {
    List<Booking> findByUser_Uuid(String userId);
}