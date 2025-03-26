package com.app.food_booking_backend.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.app.food_booking_backend.model.entity.Cart;
import com.app.food_booking_backend.model.entity.User;

@Repository
public interface CartRepository extends JpaRepository<Cart, String> {
    Cart findByUser(User user);
}