package com.app.food_booking_backend.controller;

import com.app.food_booking_backend.model.dto.AttendanceDTO;
import com.app.food_booking_backend.service.AttendanceService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/api/attendances")
public class AttendanceController {

    @Autowired
    private AttendanceService attendanceService;

    @GetMapping
    public ResponseEntity<List<AttendanceDTO>> getAllAttendances() {
        List<AttendanceDTO> attendances = attendanceService.getAllAttendances();
        return ResponseEntity.ok(attendances);
    }

    @GetMapping("/staff/{staffUuid}")
    public ResponseEntity<List<AttendanceDTO>> getAttendancesByStaff(@PathVariable String staffUuid) {
        List<AttendanceDTO> attendances = attendanceService.getAttendancesByStaff(staffUuid);
        return ResponseEntity.ok(attendances);
    }

    @GetMapping("/date-range")
    public ResponseEntity<List<AttendanceDTO>> getAttendancesByDateRange(
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate startDate,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate endDate) {
        List<AttendanceDTO> attendances = attendanceService.getAttendancesByDateRange(startDate, endDate);
        return ResponseEntity.ok(attendances);
    }

    @PostMapping
    public ResponseEntity<AttendanceDTO> createAttendance(@RequestBody AttendanceDTO attendanceDTO) {
        AttendanceDTO createdAttendance = attendanceService.createAttendance(attendanceDTO);
        return ResponseEntity.ok(createdAttendance);
    }

    @PutMapping("/{uuid}")
    public ResponseEntity<AttendanceDTO> updateAttendance(@PathVariable String uuid, @RequestBody AttendanceDTO attendanceDTO) {
        AttendanceDTO updatedAttendance = attendanceService.updateAttendance(uuid, attendanceDTO);
        return ResponseEntity.ok(updatedAttendance);
    }

    @DeleteMapping("/{uuid}")
    public ResponseEntity<Void> deleteAttendance(@PathVariable String uuid) {
        attendanceService.deleteAttendance(uuid);
        return ResponseEntity.ok().build();
    }
}