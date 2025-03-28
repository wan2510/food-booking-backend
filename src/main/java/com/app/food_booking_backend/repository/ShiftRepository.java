package com.app.food_booking_backend.repository;

import com.app.food_booking_backend.model.entity.Shift;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

@Repository
public interface ShiftRepository extends JpaRepository<Shift, Long> {
    @Query("SELECT s FROM Shift s WHERE s.date = :date AND s.startTime = :startTime AND s.endTime = :endTime AND s.staffId = :staffId")
    List<Shift> findByDateAndTimeAndStaffId(LocalDate date, LocalTime startTime, LocalTime endTime, String staffId);
}