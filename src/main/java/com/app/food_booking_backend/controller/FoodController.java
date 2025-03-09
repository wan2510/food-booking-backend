package com.app.food_booking_backend.controller;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.app.food_booking_backend.model.dto.FoodDTO;
import com.app.food_booking_backend.service.FoodService;

@RestController
@RequestMapping("/api/food")
public class FoodController {
    private final FoodService foodService;

    @Autowired
    public FoodController(FoodService foodService) {
        this.foodService = foodService;
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
}