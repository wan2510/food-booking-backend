package com.app.food_booking_backend.controller;

import com.app.food_booking_backend.model.dto.CategoryDTO;
import com.app.food_booking_backend.model.request.DeleteCategoriesRequest;
import com.app.food_booking_backend.service.CategoryService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/category")
@CrossOrigin(origins = "http://localhost:5173")
public class CategoryController {

    private final CategoryService categoryService;

    public CategoryController(CategoryService categoryService) {
        this.categoryService = categoryService;
    }

    @GetMapping
    public ResponseEntity<List<CategoryDTO>> getAllCategories() {
        List<CategoryDTO> categories = categoryService.getAllCategories();
        return ResponseEntity.ok(categories);
    }

    @PostMapping
    public ResponseEntity<CategoryDTO> createCategory(@RequestBody CategoryDTO categoryDTO) {
        CategoryDTO createdCategory = categoryService.createCategory(categoryDTO);
        return ResponseEntity.status(201).body(createdCategory);
    }

    @PutMapping("/{uuid}")
    public ResponseEntity<CategoryDTO> updateCategory(
            @PathVariable("uuid") String uuid,
            @RequestBody CategoryDTO categoryDTO) {
        CategoryDTO updatedCategory = categoryService.updateCategory(uuid, categoryDTO);
        return ResponseEntity.ok(updatedCategory);
    }

    @DeleteMapping
    public ResponseEntity<Void> deleteCategories(@RequestBody DeleteCategoriesRequest request) {
        categoryService.deleteCategories(request.getIds());
        return ResponseEntity.noContent().build();
    }
}