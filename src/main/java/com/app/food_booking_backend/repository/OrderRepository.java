package com.app.food_booking_backend.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.app.food_booking_backend.model.entity.Order;
import com.app.food_booking_backend.model.entity.User;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface OrderRepository extends JpaRepository<Order, String> {
    Optional<Order> findByUuid(String uuid);
    List<Order> findByUser(User user);
    List<Order> findByCreatedAtBetween(LocalDateTime startDate, LocalDateTime endDate);
}
