package com.app.food_booking_backend.controller;

import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.app.food_booking_backend.model.dto.TableRestaurantDTO;
import com.app.food_booking_backend.service.TableRestaurantService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/table_restaurant")
@RequiredArgsConstructor
public class TableRestaurantController {

    private final TableRestaurantService tableRestaurantService;

    @GetMapping
    public List<TableRestaurantDTO> getAllTables() {
        return tableRestaurantService.getAllTables();
    }

    @GetMapping("/{id}")
    public TableRestaurantDTO getTableById(@PathVariable Integer id) {
        return tableRestaurantService.getTableById(id);
    }
}