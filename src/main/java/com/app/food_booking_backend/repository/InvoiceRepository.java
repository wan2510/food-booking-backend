package com.app.food_booking_backend.repository;

import com.app.food_booking_backend.model.entity.Invoice;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface InvoiceRepository extends JpaRepository<Invoice, Long> {
    List<Invoice> findByStatus(String status); // Dùng cho API /past-paid và /stats/paid-by-date
}