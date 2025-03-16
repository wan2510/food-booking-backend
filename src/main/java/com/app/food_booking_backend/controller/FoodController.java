package com.app.food_booking_backend.controller;

import java.io.IOException;
import java.util.List;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.app.food_booking_backend.model.dto.FoodDTO;
import com.app.food_booking_backend.service.CloudinaryService;
import com.app.food_booking_backend.service.FoodService;

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

    @GetMapping
    public List<FoodDTO> getAllFoods() {
        return foodService.getAllFoods();
    }

    @GetMapping("/{uuid}")
    public ResponseEntity<?> getFoodByUuid(@PathVariable String uuid) {
        if (uuid == null || uuid.isBlank()) {
            return ResponseEntity.badRequest().body(Map.of("error", "UUID không hợp lệ"));
        }
        FoodDTO foodDTO = foodService.getFoodByUuid(uuid);
        if (foodDTO == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(foodDTO);
    }

    @GetMapping("/search")
    public List<FoodDTO> searchFoods(
            @RequestParam(required = false) String query,
            @RequestParam(required = false, defaultValue = "all") String categoryUuid,
            @RequestParam(required = false, defaultValue = "all") String priceFilter
    ) {
        return foodService.searchFoods(query, categoryUuid, priceFilter);
    }

    @PostMapping(value = "/{uuid}/upload-image", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<?> uploadFoodImage(
            @PathVariable String uuid,
            @RequestPart("image") MultipartFile image
    ) {
        try {
            FoodDTO foodDTO = foodService.getFoodByUuid(uuid);
            if (foodDTO == null) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND)
                        .body(Map.of("error", "Food không tồn tại"));
            }

            // Xóa ảnh cũ nếu có
            if (foodDTO.getImageUrl() != null && !foodDTO.getImageUrl().isEmpty()) {
                cloudinaryService.deleteImage(foodDTO.getImageUrl());
            }

            // Upload ảnh mới
            String imageUrl = cloudinaryService.uploadImage(image);
            
            // Cập nhật URL ảnh
            foodDTO.setImageUrl(imageUrl);
            foodService.updateFood(foodDTO);

            return ResponseEntity.ok(Map.of(
                "message", "Upload ảnh thành công",
                "imageUrl", imageUrl
            ));
        } catch (IOException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(Map.of("error", "Lỗi khi upload ảnh: " + e.getMessage()));
        }
    }

    @PutMapping("/{uuid}/update-image")
    public ResponseEntity<?> updateFoodImage(
            @PathVariable String uuid,
            @RequestBody Map<String, String> request
    ) {
        String imageUrl = request.get("imageUrl");
        if (imageUrl == null || imageUrl.isBlank()) {
            return ResponseEntity.badRequest().body(Map.of("error", "Image URL không hợp lệ"));
        }

        FoodDTO foodDTO = foodService.getFoodByUuid(uuid);
        if (foodDTO == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(Map.of("error", "Food không tồn tại"));
        }

        // Xóa ảnh cũ nếu có
        if (foodDTO.getImageUrl() != null && !foodDTO.getImageUrl().isEmpty()) {
            cloudinaryService.deleteImage(foodDTO.getImageUrl());
        }

        foodDTO.setImageUrl(imageUrl);
        foodService.updateFood(foodDTO);

        return ResponseEntity.ok(Map.of("message", "Cập nhật ảnh thành công", "imageUrl", imageUrl));
    }
}