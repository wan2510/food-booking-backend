package com.app.food_booking_backend.service;

import com.app.food_booking_backend.exception.ResourceNotFoundException;
import com.app.food_booking_backend.model.dto.ShiftDTO;
import com.app.food_booking_backend.model.entity.Shift;
import com.app.food_booking_backend.model.entity.User;
import com.app.food_booking_backend.repository.ShiftRepository;
import com.app.food_booking_backend.repository.UserRepository;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.LocalDateTime;
import java.time.YearMonth;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class ShiftService {

    private static final Logger logger = LoggerFactory.getLogger(ShiftService.class);

    @Autowired
    private ShiftRepository shiftRepository;

    @Autowired
    private UserRepository userRepository;

    /**
     * Lấy tất cả ca làm việc
     */
    public List<ShiftDTO> getAllShifts() {
        logger.info("Fetching all shifts");
        List<Shift> shifts = shiftRepository.findAll();
        return shifts.stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    /**
     * Lấy ca làm việc theo tháng và năm
     */
    public List<ShiftDTO> getShiftsByMonth(int month, int year) {
        if (month < 1 || month > 12) {
            throw new IllegalArgumentException("Month must be between 1 and 12");
        }
        if (year < 1900 || year > 9999) {
            throw new IllegalArgumentException("Year must be between 1900 and 9999");
        }

        logger.info("Fetching shifts for month: {} and year: {}", month, year);
        YearMonth yearMonth = YearMonth.of(year, month);
        LocalDate startDate = yearMonth.atDay(1);
        LocalDate endDate = yearMonth.atEndOfMonth();

        List<Shift> shifts = shiftRepository.findByDateBetween(startDate, endDate);
        return shifts.stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    /**
     * Tạo mới ca làm việc
     */
    @Transactional
    public ShiftDTO createShift(ShiftDTO shiftDTO) {
        validateShiftDTO(shiftDTO);

        logger.info("Creating new shift: {}", shiftDTO.getName());
        User staff = userRepository.findById(shiftDTO.getStaffId())
                .orElseThrow(() -> new ResourceNotFoundException("Staff not found with UUID: " + shiftDTO.getStaffId()));

        Shift shift = Shift.builder()
                .uuid(UUID.randomUUID().toString())
                .name(shiftDTO.getName())
                .date(shiftDTO.getDate())
                .startTime(shiftDTO.getStartTime())
                .endTime(shiftDTO.getEndTime())
                .staff(staff)
                .status(shiftDTO.getStatus())
                .createdAt(LocalDateTime.now())
                .updatedAt(LocalDateTime.now())
                .build();

        Shift savedShift = shiftRepository.save(shift);
        logger.info("Shift created successfully with UUID: {}", savedShift.getUuid());
        return convertToDTO(savedShift);
    }

    /**
     * Cập nhật ca làm việc
     */
    @Transactional
    public ShiftDTO updateShift(String uuid, ShiftDTO shiftDTO) {
        if (uuid == null) {
            throw new IllegalArgumentException("Shift UUID cannot be null");
        }

        validateShiftDTO(shiftDTO);

        logger.info("Updating shift with UUID: {}", uuid);
        Shift shift = shiftRepository.findById(uuid)
                .orElseThrow(() -> new ResourceNotFoundException("Shift not found with UUID: " + uuid));

        User staff = userRepository.findById(shiftDTO.getStaffId())
                .orElseThrow(() -> new ResourceNotFoundException("Staff not found with UUID: " + shiftDTO.getStaffId()));

        shift.setName(shiftDTO.getName());
        shift.setDate(shiftDTO.getDate());
        shift.setStartTime(shiftDTO.getStartTime());
        shift.setEndTime(shiftDTO.getEndTime());
        shift.setStaff(staff);
        shift.setStatus(shiftDTO.getStatus());
        shift.setUpdatedAt(LocalDateTime.now());

        Shift updatedShift = shiftRepository.save(shift);
        logger.info("Shift updated successfully with UUID: {}", updatedShift.getUuid());
        return convertToDTO(updatedShift);
    }

    /**
     * Xóa ca làm việc
     */
    @Transactional
    public void deleteShift(String uuid) {
        if (uuid == null) {
            throw new IllegalArgumentException("Shift UUID cannot be null");
        }

        logger.info("Deleting shift with UUID: {}", uuid);
        Shift shift = shiftRepository.findById(uuid)
                .orElseThrow(() -> new ResourceNotFoundException("Shift not found with UUID: " + uuid));
        shiftRepository.delete(shift);
        logger.info("Shift deleted successfully with UUID: {}", uuid);
    }

    /**
     * Chuyển đổi Shift entity thành ShiftDTO
     */
    private ShiftDTO convertToDTO(Shift shift) {
        ShiftDTO dto = new ShiftDTO();
        dto.setUuid(shift.getUuid());
        dto.setName(shift.getName());
        dto.setDate(shift.getDate());
        dto.setStartTime(shift.getStartTime());
        dto.setEndTime(shift.getEndTime());
        dto.setStaffId(shift.getStaff().getUuid());
        dto.setStaffName(shift.getStaff().getFullName());
        dto.setStatus(shift.getStatus());
        dto.setCreatedAt(shift.getCreatedAt());
        dto.setUpdatedAt(shift.getUpdatedAt());
        return dto;
    }

    /**
     * Kiểm tra dữ liệu đầu vào của ShiftDTO
     */
    private void validateShiftDTO(ShiftDTO shiftDTO) {
        if (shiftDTO.getName() == null || shiftDTO.getName().trim().isEmpty()) {
            throw new IllegalArgumentException("Shift name cannot be empty");
        }
        if (shiftDTO.getDate() == null) {
            throw new IllegalArgumentException("Shift date cannot be null");
        }
        if (shiftDTO.getStartTime() == null || shiftDTO.getEndTime() == null) {
            throw new IllegalArgumentException("Start time and end time cannot be null");
        }
        if (shiftDTO.getStaffId() == null || shiftDTO.getStaffId().trim().isEmpty()) {
            throw new IllegalArgumentException("Staff ID cannot be empty");
        }

        // Kiểm tra startTime và endTime
        LocalTime startTime = shiftDTO.getStartTime();
        LocalTime endTime = shiftDTO.getEndTime();
        if (endTime.isBefore(startTime)) {
            throw new IllegalArgumentException("End time must be after start time");
        }
    }
}