package com.app.food_booking_backend.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.app.food_booking_backend.model.entity.Food;

@Repository
public interface FoodRepository extends JpaRepository<Food, String> {
    Optional<Food> findByUuid(String uuid);
}