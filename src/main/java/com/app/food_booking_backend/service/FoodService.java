package com.app.food_booking_backend.service;

import java.util.List;

import com.app.food_booking_backend.model.request.CreateFoodRequest;
import com.app.food_booking_backend.model.request.UpdateFoodRequest;
import com.app.food_booking_backend.model.response.FoodResponse;

public interface FoodService {
    List<FoodResponse> getAllFoods();
    FoodResponse getFoodById(String uuid);
    FoodResponse createFood(CreateFoodRequest request);
    FoodResponse updateFood(String uuid, UpdateFoodRequest request);
    void deleteFood(String uuid);
}
