package com.app.food_booking_backend.service;

import com.app.food_booking_backend.model.dto.FoodDTO;
import com.app.food_booking_backend.model.request.FoodRequest;
import java.util.List;

public interface FoodService {
    List<FoodDTO> getAllFoods();
    FoodDTO getFoodById(Long id);
    FoodDTO createFood(FoodRequest request);
    FoodDTO updateFood(Long id, FoodRequest request);
    void deleteFood(Long id);
}
