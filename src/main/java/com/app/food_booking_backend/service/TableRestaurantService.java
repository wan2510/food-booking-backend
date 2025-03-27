package com.app.food_booking_backend.service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.app.food_booking_backend.model.dto.TableRestaurantDTO;
import com.app.food_booking_backend.model.entity.TableRestaurant;
import com.app.food_booking_backend.repository.TableRestaurantRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class TableRestaurantService {

    private final TableRestaurantRepository tableRestaurantRepository;

    public List<TableRestaurantDTO> getAllTables() {
        return tableRestaurantRepository.findAll()
                .stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    public TableRestaurantDTO getTableById(Integer id) {
        Optional<TableRestaurant> table = tableRestaurantRepository.findById(id);
        return table.map(this::convertToDTO).orElse(null);
    }

    private TableRestaurantDTO convertToDTO(TableRestaurant table) {
        return TableRestaurantDTO.builder()
                .id(table.getId())
                .tableNumber(table.getTableNumber())
                .capacity(table.getCapacity())
                .bookedGuests(table.getBookedGuests())
                .status(table.getStatus())
                .type(table.getType())
                .build();
    }
}