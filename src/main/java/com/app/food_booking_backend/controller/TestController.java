package com.app.food_booking_backend.controller;

import java.io.IOException;
import java.util.Map;

import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.app.food_booking_backend.service.CloudinaryService;

@RestController
@RequestMapping("/api/test")
@CrossOrigin(origins = "*", allowedHeaders = "*", methods = {org.springframework.web.bind.annotation.RequestMethod.POST})
public class TestController {

    private final CloudinaryService cloudinaryService;

    public TestController(CloudinaryService cloudinaryService) {
        this.cloudinaryService = cloudinaryService;
    }

    @PostMapping(value = "/upload", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<?> testUpload(@RequestPart("image") MultipartFile image) {
        try {
            String imageUrl = cloudinaryService.uploadImage(image);
            return ResponseEntity.ok(Map.of(
                "success", true,
                "message", "Upload ảnh thành công",
                "imageUrl", imageUrl
            ));
        } catch (IOException e) {
            return ResponseEntity.badRequest().body(Map.of(
                "success", false,
                "message", "Lỗi khi upload ảnh: " + e.getMessage()
            ));
        }
    }

    @PostMapping(value = "/upload-only", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<?> uploadOnly(@RequestPart("image") MultipartFile image) {
        try {
            String imageUrl = cloudinaryService.uploadImage(image);
            return ResponseEntity.ok(Map.of(
                "success", true,
                "message", "Upload ảnh thành công",
                "imageUrl", imageUrl
            ));
        } catch (IOException e) {
            return ResponseEntity.badRequest().body(Map.of(
                "success", false,
                "message", "Lỗi khi upload ảnh: " + e.getMessage()
            ));
        }
    }
} 