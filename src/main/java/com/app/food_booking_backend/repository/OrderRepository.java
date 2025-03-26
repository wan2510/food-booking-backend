package com.app.food_booking_backend.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.app.food_booking_backend.model.entity.Order;
import com.app.food_booking_backend.model.entity.User;

@Repository
public interface OrderRepository extends JpaRepository<Order, String> {
    Order findByUser(User user);
}