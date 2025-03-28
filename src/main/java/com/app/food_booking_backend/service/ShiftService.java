package com.app.food_booking_backend.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.app.food_booking_backend.model.dto.ShiftDTO;
import com.app.food_booking_backend.model.dto.ShiftResponseDTO;
import com.app.food_booking_backend.model.entity.Shift;
import com.app.food_booking_backend.model.entity.User;
import com.app.food_booking_backend.repository.ShiftRepository;
import com.app.food_booking_backend.repository.UserRepository;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class ShiftService {

    @Autowired
    private ShiftRepository shiftRepository;

    @Autowired
    private UserRepository userRepository;
    public List<ShiftResponseDTO> getAllShifts() {
        return shiftRepository.findAll().stream()
                .map(this::convertToResponseDTO)
                .collect(Collectors.toList());
    }

    public Shift createShift(ShiftDTO shiftDTO) {
    // Kiểm tra nhân viên
    Optional<User> userOptional = userRepository.findById(shiftDTO.getStaffId());
    if (userOptional.isEmpty()) {
        throw new IllegalArgumentException("Nhân viên không tồn tại!");
    }
    User user = userOptional.get();
    if (!"ACTIVE".equals(user.getStatus())) {
        throw new IllegalArgumentException("Nhân viên không ở trạng thái hoạt động!");
    }

    // Kiểm tra trạng thái hợp lệ
    validateStatus(shiftDTO.getStatus());

    // Chuyển đổi dữ liệu từ DTO
    try {
        LocalDate date = LocalDate.parse(shiftDTO.getDate(), DateTimeFormatter.ISO_DATE);
        LocalTime startTime = LocalTime.parse(shiftDTO.getStartTime());
        LocalTime endTime = LocalTime.parse(shiftDTO.getEndTime());

        // Kiểm tra trùng ca làm việc
        List<Shift> existingShifts = shiftRepository.findByDateAndTimeAndStaffId(date, startTime, endTime, shiftDTO.getStaffId());
        if (!existingShifts.isEmpty()) {
            throw new IllegalArgumentException("Ca làm việc đã tồn tại!");
        }

        // Tạo ca làm việc mới
        Shift shift = new Shift();
        shift.setName(shiftDTO.getName());
        shift.setDate(date);
        shift.setStartTime(startTime);
        shift.setEndTime(endTime);
        shift.setStaffId(shiftDTO.getStaffId());
        shift.setStatus(Shift.Status.valueOf(shiftDTO.getStatus()));
        shift.setNote(shiftDTO.getNote());
        shift.setCreatedAt(LocalDateTime.now());
        shift.setUpdatedAt(LocalDateTime.now());

        return shiftRepository.save(shift);
    } catch (DateTimeParseException e) {
        throw new IllegalArgumentException("Định dạng ngày hoặc giờ không hợp lệ!");
    }
}

    public Shift updateShift(Long uuid, ShiftDTO shiftDTO) {
        // Tìm ca làm việc
        Shift shift = shiftRepository.findById(uuid)
                .orElseThrow(() -> new IllegalArgumentException("Ca làm việc không tồn tại với uuid: " + uuid));

        // Kiểm tra nhân viên
        Optional<User> userOptional = userRepository.findById(shiftDTO.getStaffId());
        if (userOptional.isEmpty()) {
            throw new IllegalArgumentException("Nhân viên không tồn tại!");
        }
        User user = userOptional.get();
        if (!"ACTIVE".equals(user.getStatus())) {
            throw new IllegalArgumentException("Nhân viên không ở trạng thái hoạt động!");
        }

        // Kiểm tra trạng thái
        validateStatus(shiftDTO.getStatus());

        // Cập nhật thông tin
        shift.setName(shiftDTO.getName());
        shift.setDate(LocalDate.parse(shiftDTO.getDate(), DateTimeFormatter.ISO_DATE));
        shift.setStartTime(LocalTime.parse(shiftDTO.getStartTime()));
        shift.setEndTime(LocalTime.parse(shiftDTO.getEndTime()));
        shift.setStaffId(shiftDTO.getStaffId());
        shift.setStatus(Shift.Status.valueOf(shiftDTO.getStatus()));
        shift.setNote(shiftDTO.getNote());
        shift.setUpdatedAt(LocalDateTime.now());

        return shiftRepository.save(shift);
    }

    public void deleteShift(Long uuid) {
        if (!shiftRepository.existsById(uuid)) {
            throw new IllegalArgumentException("Ca làm việc không tồn tại với uuid: " + uuid);
        }
        shiftRepository.deleteById(uuid);
    }

    public List<ShiftResponseDTO> getShiftsByMonth(int month, int year) {
        return shiftRepository.findAll().stream()
                .filter(shift -> shift.getDate().getMonthValue() == month && shift.getDate().getYear() == year)
                .map(this::convertToResponseDTO)
                .collect(Collectors.toList());
    }

    private void validateStatus(String status) {
        try {
            Shift.Status.valueOf(status);
        } catch (IllegalArgumentException e) {
            throw new IllegalArgumentException("Giá trị trạng thái không hợp lệ: " + status + ". Các giá trị hợp lệ: " + Arrays.toString(Shift.Status.values()));
        }
    }

    private ShiftResponseDTO convertToResponseDTO(Shift shift) {
        ShiftResponseDTO dto = new ShiftResponseDTO();
        dto.setUuid(shift.getUuid());
        dto.setName(shift.getName());
        dto.setDate(shift.getDate());
        dto.setStartTime(shift.getStartTime());
        dto.setEndTime(shift.getEndTime());
        dto.setStaffId(shift.getStaffId());
        Optional<User> userOptional = userRepository.findById(shift.getStaffId());
        dto.setStaffName(userOptional.isPresent() ? userOptional.get().getFullName() : "Không xác định");
        dto.setStatus(shift.getStatus().toString());
        dto.setNote(shift.getNote() != null ? shift.getNote() : "N/A");
        return dto;
    }
}