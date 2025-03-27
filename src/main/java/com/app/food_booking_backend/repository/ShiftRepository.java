package com.app.food_booking_backend.repository;

import com.app.food_booking_backend.model.entity.Shift;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface ShiftRepository extends JpaRepository<Shift, String> {

    @Query("SELECT s FROM Shift s WHERE s.date BETWEEN :startDate AND :endDate")
    List<Shift> findByDateBetween(@Param("startDate") LocalDate startDate, @Param("endDate") LocalDate endDate);
}