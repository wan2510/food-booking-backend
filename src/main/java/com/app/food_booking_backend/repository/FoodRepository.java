package com.app.food_booking_backend.repository;


import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.app.food_booking_backend.model.entity.Category;
import com.app.food_booking_backend.model.entity.Food;

public interface FoodRepository extends JpaRepository<Food, String> {
    List<Food> findByCategory(Category category); // Tìm tất cả món ăn theo Category
    void deleteByCategory(Category category);

}