package com.app.food_booking_backend.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import org.springframework.stereotype.Repository;

import com.app.food_booking_backend.model.entity.Voucher;


@Repository
public interface VoucherRepository extends JpaRepository<Voucher, String> {
}