package com.app.food_booking_backend.service.impl;

import com.app.food_booking_backend.model.dto.FoodDTO;
import com.app.food_booking_backend.model.entity.FoodEntity;
import com.app.food_booking_backend.model.request.FoodRequest;
import com.app.food_booking_backend.repository.FoodRepository;
import com.app.food_booking_backend.service.FoodService;
import com.app.food_booking_backend.util.FoodMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class FoodServiceImpl implements FoodService {
    private final FoodRepository foodRepository;

    @Override
    public List<FoodDTO> getAllFoods() {
        return foodRepository.findAll()
                .stream()
                .map(FoodMapper::toDTO)
                .collect(Collectors.toList());
    }

    @Override
    public FoodDTO getFoodById(Long id) {
        FoodEntity food = foodRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Food not found"));
        return FoodMapper.toDTO(food);
    }

    @Override
    public FoodDTO createFood(FoodRequest request) {
        FoodEntity food = FoodEntity.builder()
                .name(request.getName())
                .description(request.getDescription())
                .price(request.getPrice())
                .rating(request.getRating())
                .build();

        foodRepository.save(food);
        return FoodMapper.toDTO(food);
    }

    @Override
    public FoodDTO updateFood(Long id, FoodRequest request) {
        FoodEntity food = foodRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Food not found"));

        food.setName(request.getName());
        food.setDescription(request.getDescription());
        food.setPrice(request.getPrice());
        food.setRating(request.getRating());

        foodRepository.save(food);
        return FoodMapper.toDTO(food);
    }

    @Override
    public void deleteFood(Long id) {
        foodRepository.deleteById(id);
    }
}
