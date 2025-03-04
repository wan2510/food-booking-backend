package com.app.food_booking_backend.controller;

import org.springframework.data.repository.query.Param;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/email")
public class EmailController {

    @GetMapping("/send-otp/{email}")
    public ResponseEntity<Boolean> sendOTP(@Param("email") String email) {
        return null;
    }

}
