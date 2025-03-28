package com.app.food_booking_backend.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.app.food_booking_backend.model.entity.Staff;

public interface StaffRepository extends JpaRepository<Staff, String> {
}
