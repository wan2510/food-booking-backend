package com.app.food_booking_backend.controller;



import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.app.food_booking_backend.model.dto.UserDTO;
import com.app.food_booking_backend.service.UserService;

import java.util.List;

@RestController
@RequestMapping("/api/users")
public class UserController {

    @Autowired
    private UserService userService;

    @GetMapping("/staff")
    public ResponseEntity<List<UserDTO>> getAllStaff() {
        return ResponseEntity.ok(userService.getAllStaff());
    }

    @GetMapping("/staff/status/{status}")
    public ResponseEntity<List<UserDTO>> getStaffByStatus(@PathVariable String status) {
        return ResponseEntity.ok(userService.getStaffByStatus(status));
    }

    @GetMapping("/staff/search")
    public ResponseEntity<List<UserDTO>> searchStaff(@RequestParam String keyword) {
        return ResponseEntity.ok(userService.searchStaff(keyword));
    }

    @PostMapping("/staff")
    public ResponseEntity<UserDTO> createStaff(@RequestBody UserDTO userDTO) {
        return ResponseEntity.ok(userService.createStaff(userDTO));
    }

    @PutMapping("/staff/{uuid}")
    public ResponseEntity<UserDTO> updateStaff(@PathVariable String uuid, @RequestBody UserDTO userDTO) {
        return ResponseEntity.ok(userService.updateStaff(uuid, userDTO));
    }

    @DeleteMapping("/staff/{uuid}")
    public ResponseEntity<Void> deleteStaff(@PathVariable String uuid) {
        userService.deleteStaff(uuid);
        return ResponseEntity.ok().build();
    }
}
