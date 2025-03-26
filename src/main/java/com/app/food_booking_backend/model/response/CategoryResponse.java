package com.app.food_booking_backend.model.response;

public class CategoryResponse {
    private String uuid;
    private String name;

    // Getters và Setters
    public String getUuid() {
        return uuid;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}