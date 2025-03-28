package com.app.food_booking_backend.service;

import com.app.food_booking_backend.model.dto.CategoryDTO;
import com.app.food_booking_backend.model.entity.Category;
import com.app.food_booking_backend.repository.CategoryRepository;
import com.app.food_booking_backend.repository.FoodRepository;

import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class CategoryService {
    private final CategoryRepository categoryRepository;
    private final FoodRepository foodRepository; // Thêm FoodRepository

    public CategoryService(CategoryRepository categoryRepository, FoodRepository foodRepository) {
        this.categoryRepository = categoryRepository;
        this.foodRepository = foodRepository;
    }

    public List<CategoryDTO> getAllCategories() {
        return categoryRepository.findAll()
                .stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    public CategoryDTO createCategory(CategoryDTO categoryDTO) {
        Category category = new Category();
        category.setName(categoryDTO.getName());
        category.setDescription(categoryDTO.getDescription());
        // Parse chuỗi ngày YYYY-MM-DD thành LocalDateTime
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        LocalDate createdDate = LocalDate.parse(categoryDTO.getCreatedAt(), formatter);
        LocalDate updatedDate = LocalDate.parse(categoryDTO.getUpdatedAt(), formatter);
        category.setCreatedAt(createdDate.atStartOfDay());
        category.setUpdatedAt(updatedDate.atStartOfDay());
        Category savedCategory = categoryRepository.save(category);
        return convertToDTO(savedCategory);
    }

    public CategoryDTO updateCategory(String uuid, CategoryDTO categoryDTO) {
        if (categoryDTO.getName() == null || categoryDTO.getName().trim().isEmpty()) {
            throw new IllegalArgumentException("Tên danh mục không được để trống!");
        }
        if (categoryDTO.getCreatedAt() == null || categoryDTO.getUpdatedAt() == null) {
            throw new IllegalArgumentException("Ngày tạo và ngày cập nhật không được để trống!");
        }

        Category category = categoryRepository.findById(uuid)
                .orElseThrow(() -> new RuntimeException("Danh mục không tồn tại!"));
        category.setName(categoryDTO.getName());
        category.setDescription(categoryDTO.getDescription() != null ? categoryDTO.getDescription() : "");
        // Parse chuỗi ngày YYYY-MM-DD thành LocalDateTime
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        LocalDate createdDate = LocalDate.parse(categoryDTO.getCreatedAt(), formatter);
        LocalDate updatedDate = LocalDate.parse(categoryDTO.getUpdatedAt(), formatter);
        category.setCreatedAt(createdDate.atStartOfDay());
        category.setUpdatedAt(updatedDate.atStartOfDay());
        Category updatedCategory = categoryRepository.save(category);
        return convertToDTO(updatedCategory);
    }

    public void deleteCategories(List<String> ids) {
        try {
            // Xóa tất cả món ăn liên quan trước
            for (String id : ids) {
                Category category = categoryRepository.findById(id)
                        .orElseThrow(() -> new RuntimeException("Danh mục không tồn tại: " + id));
                foodRepository.deleteByCategory(category);
            }
            // Sau đó xóa danh mục
            categoryRepository.deleteAllById(ids);
        } catch (Exception e) {
            throw new RuntimeException("Không thể xóa danh mục: " + e.getMessage());
        }
    }

    public Category findById(String uuid) {
        return categoryRepository.findById(uuid)
                .orElseThrow(() -> new RuntimeException("Danh mục không tồn tại!"));
    }

    public CategoryDTO convertToDTO(Category category) {
        CategoryDTO dto = new CategoryDTO();
        dto.setUuid(category.getUuid());
        dto.setName(category.getName());
        dto.setDescription(category.getDescription());
        // Chuyển LocalDateTime thành chuỗi YYYY-MM-DD để gửi về frontend
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        dto.setCreatedAt(category.getCreatedAt().format(formatter));
        dto.setUpdatedAt(category.getUpdatedAt().format(formatter));
        return dto;
    }
}