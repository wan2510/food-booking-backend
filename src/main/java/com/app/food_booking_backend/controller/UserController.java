package com.app.food_booking_backend.controller;

import java.util.List;
import java.util.Map;

import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.app.food_booking_backend.model.dto.UserDTO;
import com.app.food_booking_backend.model.entity.User;
import com.app.food_booking_backend.model.request.UpdateProfileRequest;
import com.app.food_booking_backend.service.UserService;

@RestController
@RequestMapping("/api/user")
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/getListAccount")
    public List<UserDTO> getUserDTOs() {
        return userService.getUsers();
    }

    @PostMapping("/createNewStaffAccount")
    public ResponseEntity<User> addVoucher(@RequestBody UserDTO userDTO) {
        return ResponseEntity.ok(userService.createAccountStaff(userDTO));
    }

    @PutMapping("/updateAccount")
    public ResponseEntity<?> updateVoucher(@RequestBody UserDTO userDTO) {
        try {
            return ResponseEntity.ok(userService.updateAccountUser(userDTO));
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(Map.of("error", e.getMessage()));
        }
    }

    @GetMapping("/profile")
    public ResponseEntity<UserDTO> getUserProfile() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String email = authentication.getName();
        UserDTO userDTO = userService.getUserProfile(email);
        return ResponseEntity.ok(userDTO);
    }

    @PutMapping("/profile")
    public ResponseEntity<UserDTO> updateUserProfile(@RequestBody UpdateProfileRequest updateProfileRequest) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String email = authentication.getName();
        UserDTO updatedUser = userService.updateUserProfile(email, updateProfileRequest);
        return ResponseEntity.ok(updatedUser);
    }
} 