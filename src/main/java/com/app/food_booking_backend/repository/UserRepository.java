package com.app.food_booking_backend.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.app.food_booking_backend.model.entity.User;

@Repository
public interface UserRepository extends JpaRepository<User, String> {
    User findByEmail(String email);
    User findByUuid(String uuid);
}