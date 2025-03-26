package com.app.food_booking_backend.controller;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.Files;
import java.util.List;
import java.io.IOException;
import java.util.UUID;

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import com.app.food_booking_backend.model.request.CreateFoodRequest;
import com.app.food_booking_backend.model.request.UpdateFoodRequest;
import com.app.food_booking_backend.model.response.FoodResponse;
import com.app.food_booking_backend.service.FoodService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/food")
@CrossOrigin(origins = "http://localhost:5173", allowCredentials = "true")
public class FoodController {
    private final FoodService foodService;
    private final CloudinaryService cloudinaryService;

    public FoodController(FoodService foodService, CloudinaryService cloudinaryService) {
        this.foodService = foodService;
        this.cloudinaryService = cloudinaryService;
    }

    // Lấy danh sách tất cả món ăn
    @GetMapping
    public ResponseEntity<List<FoodResponse>> getAllFoods() {
        return ResponseEntity.ok(foodService.getAllFoods());
    }

    // Lấy thông tin món ăn theo UUID
    @GetMapping("/{uuid}")
    public ResponseEntity<FoodResponse> getFoodById(@PathVariable String uuid) {
        return ResponseEntity.ok(foodService.getFoodById(uuid));
    }

    // Thêm món ăn mới
    @PostMapping
    public ResponseEntity<FoodResponse> createFood(@Valid @RequestBody CreateFoodRequest request) {
        return ResponseEntity.ok(foodService.createFood(request));
    }

    // Cập nhật món ăn
    @PutMapping("/{id}")
        public ResponseEntity<?> updateFood(
        @PathVariable("id") UUID id,
        @RequestBody UpdateFoodRequest request) {
        return ResponseEntity.ok(foodService.updateFood(id.toString(), request));
    }

    // Xóa món ăn
    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteFood(@PathVariable("id") String id) {
    System.out.println("Received ID: " + id); // Debug ID

    try {
        UUID uuid = UUID.fromString(id);  // Chuyển đổi String thành UUID
        foodService.deleteFood(uuid.toString());
        return ResponseEntity.ok().build();
    } catch (IllegalArgumentException e) {
        return ResponseEntity.badRequest().body("{\"errorMessage\": \"ID không hợp lệ: " + id + "\"}");
    }
}

    @PostMapping("/{uuid}")
    public ResponseEntity<String> uploadImage(@RequestParam("file") MultipartFile file) {
    try {
        String fileName = System.currentTimeMillis() + "_" + file.getOriginalFilename();
        Path filePath = Paths.get("uploads/" + fileName);
        Files.write(filePath, file.getBytes());
        return ResponseEntity.ok("http://localhost:8080/api/food/" + fileName);
    } catch (IOException e) {
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Lỗi upload ảnh");
    }
    }
}
