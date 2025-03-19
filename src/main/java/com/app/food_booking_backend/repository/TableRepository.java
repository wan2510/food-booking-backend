package com.app.food_booking_backend.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.app.food_booking_backend.model.entity.RestaurantTable;
import com.app.food_booking_backend.model.entity.enums.TableStatus;

@Repository
public interface TableRepository extends JpaRepository<RestaurantTable, String> {
    Optional<RestaurantTable> findById(String id);
    Optional<RestaurantTable> findByTableNumber(String tableNumber);
    List<RestaurantTable> findByStatus(TableStatus status);
    
    // For search functionality
    List<RestaurantTable> findByTableNumberContainingIgnoreCaseOrDescriptionContainingIgnoreCase(
        String tableNumber, String description);
} 