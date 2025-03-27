package com.app.food_booking_backend.controller;

import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.app.food_booking_backend.model.dto.UserDTO;
import com.app.food_booking_backend.model.request.ChangePasswordRequest;
import com.app.food_booking_backend.model.request.LoginRequest;
import com.app.food_booking_backend.model.request.RegisterRequest;
import com.app.food_booking_backend.model.request.VerifyOTPRequest;
import com.app.food_booking_backend.model.response.LoginResponse;
import com.app.food_booking_backend.service.AuthService;

import jakarta.servlet.http.HttpServletRequest;

@Deprecated
@RestController
@RequestMapping("/api/auth")
public class AuthController {

    private final AuthService authService;
    private final ModelMapper modelMapper;

    public AuthController(AuthService authService, ModelMapper modelMapper) {
        this.authService = authService;
        this.modelMapper = modelMapper;
    }

    @PostMapping("/login")
    public ResponseEntity<LoginResponse> login(@RequestBody LoginRequest loginRequest) {
        return new ResponseEntity<>(authService.login(loginRequest), HttpStatus.OK);
    }

    @PostMapping("/register")
    public ResponseEntity<UserDTO> register(@RequestBody RegisterRequest registerRequest) {
        return new ResponseEntity<>(modelMapper.map(authService.register(registerRequest), UserDTO.class), HttpStatus.OK);
    }

    @PostMapping("/verify-otp")
    public ResponseEntity<Boolean> verifyOTP(@RequestBody VerifyOTPRequest verifyOTPRequest) {
        boolean isVerified = authService.verifyOTP(verifyOTPRequest.getEmail(), verifyOTPRequest.getOtp());
        return ResponseEntity.ok(isVerified);
    }

    @GetMapping("/send-otp/{email}")
    public boolean sendOTP(@PathVariable("email") String email) {
        authService.sendOTP(email);
        return true;
    }
    
    @PutMapping("/change-password")
    public ResponseEntity<String> changePassword(@RequestBody ChangePasswordRequest changePasswordRequest,
                                                   HttpServletRequest request) {
        String email = request.getUserPrincipal().getName();
        authService.changePassword(email, changePasswordRequest);
        return ResponseEntity.ok("Mật khẩu đã được thay đổi thành công!");
    }
}
