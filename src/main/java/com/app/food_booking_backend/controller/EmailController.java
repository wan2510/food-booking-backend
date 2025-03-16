package com.app.food_booking_backend.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.app.food_booking_backend.service.AuthService;

@RestController
@RequestMapping("/api/email")
public class EmailController {

    private final AuthService authService;

    public EmailController(AuthService authService) {
        this.authService = authService;
    }

    @GetMapping("/send-otp/{email}")
    public boolean sendOTP(@PathVariable(name = "email") String email) {
        authService.sendOTP(email);
        return true;
    }
}
