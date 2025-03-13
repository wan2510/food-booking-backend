package com.app.food_booking_backend.service;

import com.app.food_booking_backend.model.dto.RestaurantTableDTO;
import com.app.food_booking_backend.model.entity.RestaurantTable;
import com.app.food_booking_backend.repository.RestaurantTableRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class RestaurantTableService {
    private final RestaurantTableRepository repository;

    public RestaurantTableService(RestaurantTableRepository repository) {
        this.repository = repository;
    }

    public List<RestaurantTable> getAllTables() {
        return repository.findAll();
    }

    public RestaurantTable getTableById(Integer id) {
        return repository.findById(id).orElseThrow(() -> new RuntimeException("Table not found"));
    }

    public RestaurantTable createTable(RestaurantTableDTO tableDTO) {
        RestaurantTable table = new RestaurantTable();
        table.setNumber(tableDTO.getNumber());
        table.setDescription(tableDTO.getDescription());
        table.setStatus(tableDTO.getStatus());
        table.setMaxNumberHuman(tableDTO.getMaxNumberHuman());
        table.setCreatedAt(LocalDateTime.now());
        table.setUpdatedAt(LocalDateTime.now());
        return repository.save(table);
    }

    public RestaurantTable updateTable(Integer id, RestaurantTableDTO tableDTO) {
        RestaurantTable table = repository.findById(id).orElseThrow(() -> new RuntimeException("Table not found"));
        table.setNumber(tableDTO.getNumber());
        table.setDescription(tableDTO.getDescription());
        table.setStatus(tableDTO.getStatus());
        table.setMaxNumberHuman(tableDTO.getMaxNumberHuman());
        table.setUpdatedAt(LocalDateTime.now());
        return repository.save(table);
    }

    public void deleteTable(Integer id) {
        repository.deleteById(id);
    }
}
