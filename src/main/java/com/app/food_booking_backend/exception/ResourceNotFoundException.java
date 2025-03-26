package com.app.food_booking_backend.exception;

/**
 * Ngoại lệ khi không tìm thấy tài nguyên (ví dụ: hóa đơn không tồn tại).
 */
public class ResourceNotFoundException extends RuntimeException {
    public ResourceNotFoundException(String message) {
        super(message);
    }
}