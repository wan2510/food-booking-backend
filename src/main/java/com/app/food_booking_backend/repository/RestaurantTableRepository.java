package com.app.food_booking_backend.repository;

import com.app.food_booking_backend.model.entity.RestaurantTable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RestaurantTableRepository extends JpaRepository<RestaurantTable, Integer> {}
