package com.app.food_booking_backend.model.request.table;

import com.app.food_booking_backend.model.entity.enums.TableStatus;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UpdateTableRequest {
    
    @Pattern(regexp = "^[A-Z][0-9]{2}$", message = "Số bàn phải có định dạng [Chữ cái][Số], ví dụ: A01, B02")
    @Size(max = 10, message = "Số bàn không được vượt quá 10 ký tự")
    private String tableNumber;
    
    @Size(min = 5, max = 200, message = "Mô tả phải từ 5 đến 200 ký tự")
    private String description;
    
    @Min(value = 1, message = "Sức chứa phải lớn hơn 0")
    @Max(value = 20, message = "Sức chứa không được vượt quá 20")
    private Integer capacity;
    
    private TableStatus status;
} 