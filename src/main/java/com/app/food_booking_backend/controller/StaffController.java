package com.app.food_booking_backend.controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import com.app.food_booking_backend.model.dto.StaffDTO;
import com.app.food_booking_backend.service.StaffService;

import java.util.List;

@RestController
@RequestMapping("/api/staff")
@CrossOrigin(origins = "http://localhost:5173", allowCredentials = "true")
public class StaffController {

    @Autowired
    private StaffService staffService;

    @GetMapping
    public List<StaffDTO> getAllStaff() {
        return staffService.getAllStaff();
    }
}
