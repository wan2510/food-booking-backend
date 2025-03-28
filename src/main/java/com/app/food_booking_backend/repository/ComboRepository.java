package com.app.food_booking_backend.repository;

import com.app.food_booking_backend.model.entity.Combo;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ComboRepository extends JpaRepository<Combo, String> {
}