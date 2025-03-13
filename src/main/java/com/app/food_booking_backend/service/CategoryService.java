package com.app.food_booking_backend.service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.app.food_booking_backend.model.dto.CategoryDTO;
import com.app.food_booking_backend.model.entity.Category;
import com.app.food_booking_backend.repository.CategoryRepository;

@Service
public class CategoryService {
    private final CategoryRepository categoryRepository;

    public CategoryService(CategoryRepository categoryRepository) {
        this.categoryRepository = categoryRepository;
    }

    public List<CategoryDTO> getAllCategories() {
        return categoryRepository.findAll()
                .stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    private CategoryDTO convertToDTO(Category category) {
        CategoryDTO dto = new CategoryDTO();
        dto.setUuid(category.getUuid());
        dto.setName(category.getName());
        dto.setDescription(category.getDescription());
        return dto;
    }
}