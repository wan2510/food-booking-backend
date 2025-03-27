package com.app.food_booking_backend.repository;

import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.app.food_booking_backend.model.entity.TableRestaurant;

@Repository
public interface TableRestaurantRepository extends JpaRepository<TableRestaurant, Integer> {
    Optional<TableRestaurant> findByTableNumber(String tableNumber);
}