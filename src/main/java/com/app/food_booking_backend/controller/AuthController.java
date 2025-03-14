package com.app.food_booking_backend.controller;

import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.app.food_booking_backend.model.dto.ResetPasswordRequestDTO;
import com.app.food_booking_backend.model.dto.UserDTO;
import com.app.food_booking_backend.model.request.LoginRequest;
import com.app.food_booking_backend.model.request.RegisterRequest;
import com.app.food_booking_backend.model.request.VerifyOTPRequest;
import com.app.food_booking_backend.model.response.LoginResponse;
import com.app.food_booking_backend.service.AuthService;

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
    public ResponseEntity<String> sendOTP(@PathVariable String email) {
        authService.sendOTP(email);
        return ResponseEntity.ok("OTP đã được gửi tới " + email);
    }

    @PostMapping("/reset-password")
    public ResponseEntity<String> resetPassword(@RequestBody ResetPasswordRequestDTO resetPasswordRequest) {
        try {
            boolean isReset = authService.resetPassword(
                    resetPasswordRequest.getEmail(),
                    resetPasswordRequest.getOtp(),
                    resetPasswordRequest.getNewPassword()
            );

            if (isReset) {
                return ResponseEntity.ok("Mật khẩu đã được cập nhật thành công!");
            } else {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("OTP không hợp lệ hoặc đã hết hạn!");
            }
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Có lỗi xảy ra khi đặt lại mật khẩu.");
        }
    }
}