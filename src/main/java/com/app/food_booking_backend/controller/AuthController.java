package com.app.food_booking_backend.controller;

import com.app.food_booking_backend.model.dto.UserDTO;
import com.app.food_booking_backend.model.request.LoginRequest;
import com.app.food_booking_backend.model.request.RegisterRequest;
import com.app.food_booking_backend.model.request.VerifyOTPRequest;
import com.app.food_booking_backend.model.response.LoginResponse;
import com.app.food_booking_backend.service.AuthService;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
        return null;
    }
}
