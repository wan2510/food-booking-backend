package com.app.food_booking_backend.model.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ApiResponse<T> {
    private boolean success;
    private String message;
    private T data;
    
    public static <T> ApiResponse<T> success(T data) {
        return ApiResponse.<T>builder()
                .success(true)
                .data(data)
                .build();
    }
    
    public static <T> ApiResponse<T> success(String message, T data) {
        return ApiResponse.<T>builder()
                .success(true)
                .message(message)
                .data(data)
                .build();
    }
    
    public static ApiResponse<?> success(String message) {
        return ApiResponse.builder()
                .success(true)
                .message(message)
                .build();
    }
    
    public static ApiResponse<?> error(String message) {
        return ApiResponse.builder()
                .success(false)
                .message(message)
                .build();
    }
} 