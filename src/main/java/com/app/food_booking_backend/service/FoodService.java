package com.app.food_booking_backend.service;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.app.food_booking_backend.model.dto.FoodDTO;
import com.app.food_booking_backend.model.entity.Food;
import com.app.food_booking_backend.repository.FoodRepository;

@Service
public class FoodService {

    private final FoodRepository foodRepository;

    public FoodService(FoodRepository foodRepository) {
        this.foodRepository = foodRepository;
    }

    public List<FoodDTO> getAllFoods() {
        return foodRepository.findAll()
                             .stream()
                             .map(this::convertToDTO)
                             .collect(Collectors.toList());
    }

    // Lấy FoodDTO theo UUID
    public FoodDTO getFoodByUuid(String uuid) {
        Optional<Food> foodOpt = foodRepository.findById(uuid);
        return foodOpt.map(this::convertToDTO).orElse(null);
    }
    public FoodDTO updateFood(FoodDTO foodDTO) {
        // 1) Tìm entity trong DB theo uuid
        Optional<Food> foodOpt = foodRepository.findById(foodDTO.getUuid());
        if (foodOpt.isEmpty()) {
            throw new RuntimeException("Food không tồn tại với uuid: " + foodDTO.getUuid());
        }
        Food food = foodOpt.get();

        // 2) Cập nhật các trường
        food.setName(foodDTO.getName());
        food.setDescription(foodDTO.getDescription());
        food.setStatus(foodDTO.getStatus());
        food.setImageUrl(foodDTO.getImageUrl());
        food.setPrice(foodDTO.getPrice());
        // Nếu cần cập nhật Category, bạn phải có CategoryRepository
        // rồi setCategory(...) theo UUID hoặc ID của Category.

        // 3) Lưu lại DB
        Food savedFood = foodRepository.save(food);

        // 4) Trả về DTO sau khi update
        return convertToDTO(savedFood);
    }

    private FoodDTO convertToDTO(Food food) {
        return new FoodDTO(
                food.getUuid(),
                food.getName(),
                food.getDescription(),
                food.getStatus(),
                food.getImageUrl(),
                food.getPrice(),
                food.getCategory().getName() // Hiển thị tên Category
        );
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
                // Giá dưới 50K
                foods = foods.stream()
                             .filter(food -> food.getPrice().compareTo(new BigDecimal("50000")) < 0)
                             .collect(Collectors.toList());
            } else if ("medium".equals(priceFilter)) {
                // Giá từ 50K đến 100K
                foods = foods.stream()
                             .filter(food -> food.getPrice().compareTo(new BigDecimal("50000")) >= 0
                                    && food.getPrice().compareTo(new BigDecimal("100000")) <= 0)
                             .collect(Collectors.toList());
            } else if ("high".equals(priceFilter)) {
                // Giá trên 100K
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