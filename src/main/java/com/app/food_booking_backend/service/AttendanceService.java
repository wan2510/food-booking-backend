package com.app.food_booking_backend.service;

import com.app.food_booking_backend.model.dto.AttendanceDTO;
import com.app.food_booking_backend.model.entity.Attendance;
import com.app.food_booking_backend.model.entity.User;
import com.app.food_booking_backend.repository.AttendanceRepository;
import com.app.food_booking_backend.repository.UserRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class AttendanceService {

    @Autowired
    private AttendanceRepository attendanceRepository;

    @Autowired
    private UserRepository userRepository;

    public List<AttendanceDTO> getAllAttendances() {
        return attendanceRepository.findAll().stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    public List<AttendanceDTO> getAttendancesByStaff(String staffUuid) {
        return attendanceRepository.findByStaff_Uuid(staffUuid).stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    public List<AttendanceDTO> getAttendancesByDateRange(LocalDate startDate, LocalDate endDate) {
        return attendanceRepository.findByDateBetween(startDate, endDate).stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    public AttendanceDTO createAttendance(AttendanceDTO attendanceDTO) {
        User staff = userRepository.findById(attendanceDTO.getStaffId())
                .orElseThrow(() -> new RuntimeException("Staff not found with UUID: " + attendanceDTO.getStaffId()));

        Attendance attendance = Attendance.builder()
                .uuid(UUID.randomUUID().toString())
                .staff(staff)
                .date(attendanceDTO.getDate() != null ? attendanceDTO.getDate() : LocalDate.now())
                .checkIn(attendanceDTO.getCheckIn())
                .checkOut(attendanceDTO.getCheckOut())
                .note(attendanceDTO.getNote())
                .createdAt(LocalDateTime.now())
                .updatedAt(LocalDateTime.now())
                .build();

        attendance = attendanceRepository.save(attendance);
        return convertToDTO(attendance);
    }

    public AttendanceDTO updateAttendance(String uuid, AttendanceDTO attendanceDTO) {
        Attendance attendance = attendanceRepository.findById(uuid)
                .orElseThrow(() -> new RuntimeException("Attendance not found with UUID: " + uuid));

        User staff = userRepository.findById(attendanceDTO.getStaffId())
                .orElseThrow(() -> new RuntimeException("Staff not found with UUID: " + attendanceDTO.getStaffId()));

        attendance.setStaff(staff);
        attendance.setDate(attendanceDTO.getDate() != null ? attendanceDTO.getDate() : attendance.getDate());
        attendance.setCheckIn(attendanceDTO.getCheckIn());
        attendance.setCheckOut(attendanceDTO.getCheckOut());
        attendance.setNote(attendanceDTO.getNote());
        attendance.setUpdatedAt(LocalDateTime.now());

        attendance = attendanceRepository.save(attendance);
        return convertToDTO(attendance);
    }

    public void deleteAttendance(String uuid) {
        Attendance attendance = attendanceRepository.findById(uuid)
                .orElseThrow(() -> new RuntimeException("Attendance not found with UUID: " + uuid));
        attendanceRepository.delete(attendance);
    }

    private AttendanceDTO convertToDTO(Attendance attendance) {
        AttendanceDTO dto = new AttendanceDTO();
        dto.setUuid(attendance.getUuid());
        dto.setStaffId(attendance.getStaff().getUuid());
        dto.setStaffName(attendance.getStaff().getFullName());
        dto.setDate(attendance.getDate());
        dto.setCheckIn(attendance.getCheckIn());
        dto.setCheckOut(attendance.getCheckOut());
        dto.setNote(attendance.getNote());
        dto.setCreatedAt(attendance.getCreatedAt());
        dto.setUpdatedAt(attendance.getUpdatedAt());
        return dto;
    }
}