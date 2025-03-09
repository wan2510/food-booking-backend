package com.app.food_booking_backend.service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.app.food_booking_backend.model.dto.FoodDTO;
import com.app.food_booking_backend.model.entity.Food;
import com.app.food_booking_backend.repository.FoodRepository;

@Service
public class FoodService {
    private final FoodRepository foodRepository;

    @Autowired
    public FoodService(FoodRepository foodRepository) {
        this.foodRepository = foodRepository;
    }

    public List<FoodDTO> getAllFoods() {
        return foodRepository.findAll().stream().map(this::convertToDTO).collect(Collectors.toList());
    }

    public FoodDTO getFoodByUuid(String uuid) {
        Optional<Food> foodOpt = foodRepository.findById(uuid);
        return foodOpt.map(this::convertToDTO).orElse(null);
    }
    private FoodDTO convertToDTO(Food food) {
        return new FoodDTO(
            food.getUuid(),
            food.getName(),
            food.getDescription(),
            food.getStatus(),
            food.getImageUrl(),
            food.getPrice(),
            food.getCategory().getName()
        );
    }
}
