package com.app.food_booking_backend.model.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CategoryDTO {
    private String uuid;
    private String name;
    private String description;
    private String createdAt; // Đổi thành String để nhận chuỗi từ frontend
    private String updatedAt; // Đổi thành String để nhận chuỗi từ frontend
}