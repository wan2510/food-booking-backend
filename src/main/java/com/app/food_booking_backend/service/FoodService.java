package com.app.food_booking_backend.service;

import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import com.app.food_booking_backend.model.dto.FoodDTO;
import com.app.food_booking_backend.model.entity.Food;
import com.app.food_booking_backend.repository.FoodRepository;

@Service
public class FoodService {

    private final FoodRepository foodRepository;
    private final ModelMapper modelMapper;

    public FoodService(FoodRepository foodRepository, ModelMapper modelMapper) {
        this.foodRepository = foodRepository;
        this.modelMapper = modelMapper;
    }

    public List<FoodDTO> getAllFoods() {
        return foodRepository.findAll()
                             .stream()
                             .map(this::convertToDTO)
                             .collect(Collectors.toList());
    }

    public FoodDTO getFoodByUuid(String uuid) {
        return foodRepository.findById(uuid)
                             .map(this::convertToDTO)
                             .orElse(null);
    }

    public FoodDTO updateFood(FoodDTO foodDTO) {
        Food food = foodRepository.findById(foodDTO.getUuid())
                                  .orElseThrow(() -> new RuntimeException("Food không tồn tại với uuid: " + foodDTO.getUuid()));

        food.setName(foodDTO.getName());
        food.setDescription(foodDTO.getDescription());
        food.setStatus(foodDTO.getStatus());
        food.setImageUrl(foodDTO.getImageUrl());
        food.setPrice(foodDTO.getPrice());

        return convertToDTO(foodRepository.save(food));
    }

    private FoodDTO convertToDTO(Food food) {
        FoodDTO foodDTO = modelMapper.map(food, FoodDTO.class);
        foodDTO.setCategoryName(food.getCategory().getName());
        return foodDTO;
    }

    public List<FoodDTO> searchFoods(String query, String categoryUuid, String priceFilter) {
        List<Food> foods = foodRepository.findAll();

        if (query != null && !query.isBlank()) {
            String lowerQuery = query.toLowerCase();
            foods = foods.stream()
                         .filter(food -> food.getName().toLowerCase().contains(lowerQuery))
                         .collect(Collectors.toList());
        }

        if (categoryUuid != null && !"all".equals(categoryUuid)) {
            foods = foods.stream()
                         .filter(food -> food.getCategory().getUuid().equals(categoryUuid))
                         .collect(Collectors.toList());
        }

        if (priceFilter != null && !"all".equals(priceFilter)) {
            if ("low".equals(priceFilter)) {
                foods = foods.stream()
                             .filter(food -> food.getPrice().compareTo(new BigDecimal("50000")) < 0)
                             .collect(Collectors.toList());
            } else if ("medium".equals(priceFilter)) {
                foods = foods.stream()
                             .filter(food -> food.getPrice().compareTo(new BigDecimal("50000")) >= 0
                                    && food.getPrice().compareTo(new BigDecimal("100000")) <= 0)
                             .collect(Collectors.toList());
            } else if ("high".equals(priceFilter)) {
                foods = foods.stream()
                             .filter(food -> food.getPrice().compareTo(new BigDecimal("100000")) > 0)
                             .collect(Collectors.toList());
            }
        }

        return foods.stream()
                    .map(this::convertToDTO)
                    .collect(Collectors.toList());
    }
}