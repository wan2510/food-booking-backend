package com.app.food_booking_backend.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.app.food_booking_backend.model.entity.Food;

public interface FoodRepository extends JpaRepository<Food, String> {

}