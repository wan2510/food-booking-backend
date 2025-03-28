package com.app.food_booking_backend.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.app.food_booking_backend.model.dto.ShiftDTO;
import com.app.food_booking_backend.model.entity.Shift;
import com.app.food_booking_backend.service.ShiftService;
import com.app.food_booking_backend.model.dto.ShiftResponseDTO;

import java.util.List;

@RestController
@RequestMapping("/api/shifts")
@CrossOrigin(origins = "http://localhost:5173")
public class ShiftController {

    @Autowired
    private ShiftService shiftService;

    @GetMapping
    public ResponseEntity<List<ShiftResponseDTO>> getAllShifts() {
        return ResponseEntity.ok(shiftService.getAllShifts());
    }

    @PostMapping
public ResponseEntity<?> createShift(@RequestBody ShiftDTO shiftDTO) {
    try {
        // Kiểm tra dữ liệu đầu vào
        if (shiftDTO.getName() == null || shiftDTO.getName().isBlank()) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ErrorResponse("Tên ca làm việc không được để trống!"));
        }
        if (shiftDTO.getDate() == null || shiftDTO.getStartTime() == null || shiftDTO.getEndTime() == null) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ErrorResponse("Ngày, giờ bắt đầu, giờ kết thúc không hợp lệ!"));
        }
        if (shiftDTO.getStaffId() == null || shiftDTO.getStaffId().isBlank()) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ErrorResponse("Nhân viên không hợp lệ!"));
        }
        if (shiftDTO.getStatus() == null || shiftDTO.getStatus().isBlank()) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ErrorResponse("Trạng thái ca làm việc không hợp lệ!"));
        }

        Shift createdShift = shiftService.createShift(shiftDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdShift);
    } catch (IllegalArgumentException e) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ErrorResponse(e.getMessage()));
    }
}


    @PutMapping("/{uuid}")
    public ResponseEntity<?> updateShift(@PathVariable("uuid") Long uuid, @RequestBody ShiftDTO shiftDTO) {
        try {
            Shift updatedShift = shiftService.updateShift(uuid, shiftDTO);
            return ResponseEntity.ok(updatedShift);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(new ErrorResponse(e.getMessage()));
        }
    }

    @DeleteMapping("/{uuid}")
    public ResponseEntity<?> deleteShift(@PathVariable("uuid") Long uuid) {
        try {
            shiftService.deleteShift(uuid);
            return ResponseEntity.ok().build();
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(new ErrorResponse(e.getMessage()));
        }
    }

    @GetMapping("/by-month")
    public ResponseEntity<List<ShiftResponseDTO>> getShiftsByMonth(@RequestParam int month, @RequestParam int year) {
        return ResponseEntity.ok(shiftService.getShiftsByMonth(month, year));
    }
}

class ErrorResponse {
    private String errorMessage;

    public ErrorResponse(String errorMessage) {
        this.errorMessage = errorMessage;
    }

    public String getErrorMessage() {
        return errorMessage;
    }

    public void setErrorMessage(String errorMessage) {
        this.errorMessage = errorMessage;
    }
}