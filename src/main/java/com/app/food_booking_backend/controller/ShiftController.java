package com.app.food_booking_backend.controller;

import com.app.food_booking_backend.model.dto.ShiftDTO;
import com.app.food_booking_backend.service.ShiftService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/shifts")
public class ShiftController {

    @Autowired
    private ShiftService shiftService;

    @GetMapping
    public ResponseEntity<List<ShiftDTO>> getAllShifts() {
        List<ShiftDTO> shifts = shiftService.getAllShifts();
        return ResponseEntity.ok(shifts);
    }

    @GetMapping("/month")
    public ResponseEntity<List<ShiftDTO>> getShiftsByMonth(
            @RequestParam int month,
            @RequestParam int year) {
        List<ShiftDTO> shifts = shiftService.getShiftsByMonth(month, year);
        return ResponseEntity.ok(shifts);
    }

    @PostMapping
    public ResponseEntity<ShiftDTO> createShift(@RequestBody ShiftDTO shiftDTO) {
        ShiftDTO createdShift = shiftService.createShift(shiftDTO);
        return ResponseEntity.ok(createdShift);
    }

    @PutMapping("/{uuid}")
    public ResponseEntity<ShiftDTO> updateShift(@PathVariable String uuid, @RequestBody ShiftDTO shiftDTO) {
        ShiftDTO updatedShift = shiftService.updateShift(uuid, shiftDTO);
        return ResponseEntity.ok(updatedShift);
    }

    @DeleteMapping("/{uuid}")
    public ResponseEntity<Void> deleteShift(@PathVariable String uuid) {
        shiftService.deleteShift(uuid);
        return ResponseEntity.ok().build();
    }
}