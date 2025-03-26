package com.app.food_booking_backend.repository;

import com.app.food_booking_backend.model.entity.Attendance;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface AttendanceRepository extends JpaRepository<Attendance, String> {

    @Query("SELECT a FROM Attendance a WHERE a.staff.uuid = :staffUuid")
    List<Attendance> findByStaff_Uuid(@Param("staffUuid") String staffUuid);

    @Query("SELECT a FROM Attendance a WHERE a.date BETWEEN :startDate AND :endDate")
    List<Attendance> findByDateBetween(@Param("startDate") LocalDate startDate, @Param("endDate") LocalDate endDate);
}