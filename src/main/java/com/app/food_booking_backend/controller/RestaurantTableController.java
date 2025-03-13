package com.app.food_booking_backend.controller;

import com.app.food_booking_backend.model.dto.RestaurantTableDTO;
import com.app.food_booking_backend.model.entity.RestaurantTable;
import com.app.food_booking_backend.service.RestaurantTableService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/tables")
public class RestaurantTableController {
    private final RestaurantTableService service;

    public RestaurantTableController(RestaurantTableService service) {
        this.service = service;
    }

    @GetMapping
    public List<RestaurantTable> getAllTables() {
        return service.getAllTables();
    }

    @GetMapping("/{id}")
    public RestaurantTable getTableById(@PathVariable Integer id) {
        return service.getTableById(id);
    }

    @PostMapping
    public RestaurantTable createTable(@RequestBody RestaurantTableDTO tableDTO) {
        return service.createTable(tableDTO);
    }

    @PutMapping("/{id}")
    public RestaurantTable updateTable(@PathVariable Integer id, @RequestBody RestaurantTableDTO tableDTO) {
        return service.updateTable(id, tableDTO);
    }

    @DeleteMapping("/{id}")
    public void deleteTable(@PathVariable Integer id) {
        service.deleteTable(id);
    }
}
