package com.app.food_booking_backend.service.impl;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.app.food_booking_backend.model.entity.Category;
import com.app.food_booking_backend.model.entity.Food;
import com.app.food_booking_backend.model.request.CreateFoodRequest;
import com.app.food_booking_backend.model.request.UpdateFoodRequest;
import com.app.food_booking_backend.model.response.FoodResponse;
import com.app.food_booking_backend.repository.CategoryRepository;
import com.app.food_booking_backend.repository.FoodRepository;
import com.app.food_booking_backend.service.FoodService;

@Service
public class FoodServiceImpl implements FoodService {
    private final FoodRepository foodRepository;
    private final CategoryRepository categoryRepository;

    public FoodServiceImpl(FoodRepository foodRepository, CategoryRepository categoryRepository) {
        this.foodRepository = foodRepository;
        this.categoryRepository = categoryRepository;
    }

    @Override
    public List<FoodResponse> getAllFoods() {
        return foodRepository.findAll().stream()
                .map(this::convertToResponse)
                .collect(Collectors.toList());
    }

    @Override
    public FoodResponse getFoodById(String uuid) {
        Food food = foodRepository.findById(uuid)
                .orElseThrow(() -> new RuntimeException("Food not found"));
        return convertToResponse(food);
    }

    @Override
    @Transactional
    public FoodResponse createFood(CreateFoodRequest request) {
        Category category = categoryRepository.findById(request.getCategoryId())
                .orElseThrow(() -> new RuntimeException("Category not found"));

        Food food = Food.builder()
                .uuid(UUID.randomUUID().toString())
                .name(request.getName())
                .description(request.getDescription())
                .status(request.getStatus())
                .category(category)
                .imageUrl(request.getImageUrl())
                .price(request.getPrice() != null ? request.getPrice() : null)
                .build();

        foodRepository.save(food);
        return convertToResponse(food);
    }

    @Override
    @Transactional
    public FoodResponse updateFood(String uuid, UpdateFoodRequest request) {
        Food food = foodRepository.findById(uuid)
                .orElseThrow(() -> new RuntimeException("Food not found"));

        if (request.getName() != null) food.setName(request.getName());
        if (request.getDescription() != null) food.setDescription(request.getDescription());
        if (request.getStatus() != null) food.setStatus(request.getStatus());
        if (request.getCategoryId() != null) {
            Category category = categoryRepository.findById(request.getCategoryId())
                    .orElseThrow(() -> new RuntimeException("Category not found"));
            food.setCategory(category);
        }
        if (request.getImageUrl() != null) food.setImageUrl(request.getImageUrl());
        if (request.getPrice() != null) food.setPrice(request.getPrice());

        foodRepository.save(food);
        return convertToResponse(food);
    }

    @Override
    @Transactional
    public void deleteFood(String uuid) {
        Food food = foodRepository.findById(uuid)
                .orElseThrow(() -> new RuntimeException("Food not found"));
        foodRepository.delete(food);
    }

    private FoodResponse convertToResponse(Food food) {
        return FoodResponse.builder()
                .uuid(food.getUuid())
                .name(food.getName())
                .description(food.getDescription())
                .status(food.getStatus())
                .categoryId(food.getCategory().getUuid())
                .categoryName(food.getCategory().getName())
                .imageUrl(food.getImageUrl())
                .price(food.getPrice())
                .createdAt(food.getCreatedAt())
                .updatedAt(food.getUpdatedAt())
                .build();
    }
}
