package com.app.food_booking_backend.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/api/upload")
@CrossOrigin(origins = "http://localhost:5173") // Bật CORS chỉ cho API này
public class UploadController {

    @PostMapping
    public ResponseEntity<String> uploadFile(@RequestParam("file") MultipartFile file) {
        return ResponseEntity.ok("Upload thành công: " + file.getOriginalFilename());
    }
}

