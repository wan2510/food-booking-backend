package com.app.food_booking_backend.model.dto;

import java.time.LocalDate;

public class PaidByDateStats {
    private LocalDate date;
    private int count;
    private double amount;

    public PaidByDateStats(LocalDate date, int count, double amount) {
        this.date = date;
        this.count = count;
        this.amount = amount;
    }

    // Getters và Setters
    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }
}