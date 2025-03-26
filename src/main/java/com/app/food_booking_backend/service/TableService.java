package com.app.food_booking_backend.service;

import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.app.food_booking_backend.exception.ResourceNotFoundException;
import com.app.food_booking_backend.model.dto.TableDTO;
import com.app.food_booking_backend.model.entity.RestaurantTable;
import com.app.food_booking_backend.model.entity.enums.TableStatus;
import com.app.food_booking_backend.model.request.table.CreateTableRequest;
import com.app.food_booking_backend.model.request.table.UpdateTableRequest;
import com.app.food_booking_backend.model.response.PagedResponse;
import com.app.food_booking_backend.repository.TableRepository;

@Service
public class TableService {

    private final TableRepository tableRepository;
    private final ModelMapper modelMapper;

    public TableService(TableRepository tableRepository, ModelMapper modelMapper) {
        this.tableRepository = tableRepository;
        this.modelMapper = modelMapper;
    }

    public PagedResponse<TableDTO> getAllTables(int page, int limit, String search, TableStatus status) {
        page = Math.max(0, page - 1); // Convert from 1-based to 0-based indexing
        limit = Math.max(1, limit);
        
        Pageable pageable = PageRequest.of(page, limit);
        Page<RestaurantTable> tablesPage;
        
        if (search != null && !search.isBlank()) {
            if (status != null) {
                // Filter by search and status
                String searchTerm = search.trim();
                tablesPage = tableRepository.findAll(pageable);
                List<RestaurantTable> filteredTables = tablesPage.getContent().stream()
                        .filter(table -> (table.getTableNumber().toLowerCase().contains(searchTerm.toLowerCase()) 
                                || table.getDescription().toLowerCase().contains(searchTerm.toLowerCase()))
                                && table.getStatus() == status)
                        .collect(Collectors.toList());
                
                // Manual pagination for filtered results
                int start = (int) pageable.getOffset();
                int end = Math.min((start + pageable.getPageSize()), filteredTables.size());
                
                List<RestaurantTable> pageContent = start < end ? 
                        filteredTables.subList(start, end) : 
                        List.of();
                
                // Convert to DTOs
                List<TableDTO> tableDTOs = pageContent.stream()
                        .map(this::convertToDTO)
                        .collect(Collectors.toList());
                
                return PagedResponse.<TableDTO>builder()
                        .content(tableDTOs)
                        .pagination(PagedResponse.PaginationInfo.builder()
                                .total(filteredTables.size())
                                .page(page + 1)
                                .limit(limit)
                                .build())
                        .build();
            } else {
                // Filter by search only
                tablesPage = tableRepository.findAll(pageable);
                List<RestaurantTable> searchResults = tablesPage.getContent().stream()
                        .filter(table -> table.getTableNumber().toLowerCase().contains(search.toLowerCase()) 
                                || table.getDescription().toLowerCase().contains(search.toLowerCase()))
                        .collect(Collectors.toList());
                
                // Convert to DTOs
                List<TableDTO> tableDTOs = searchResults.stream()
                        .map(this::convertToDTO)
                        .collect(Collectors.toList());
                
                return PagedResponse.<TableDTO>builder()
                        .content(tableDTOs)
                        .pagination(PagedResponse.PaginationInfo.builder()
                                .total((long) searchResults.size())
                                .page(page + 1)
                                .limit(limit)
                                .build())
                        .build();
            }
        } else if (status != null) {
            // Filter by status only
            List<RestaurantTable> tablesByStatus = tableRepository.findByStatus(status);
            
            // Manual pagination
            int start = (int) pageable.getOffset();
            int end = Math.min((start + pageable.getPageSize()), tablesByStatus.size());
            
            List<RestaurantTable> pageContent = start < end ? 
                    tablesByStatus.subList(start, end) : 
                    List.of();
            
            // Convert to DTOs
            List<TableDTO> tableDTOs = pageContent.stream()
                    .map(this::convertToDTO)
                    .collect(Collectors.toList());
            
            return PagedResponse.<TableDTO>builder()
                    .content(tableDTOs)
                    .pagination(PagedResponse.PaginationInfo.builder()
                            .total((long) tablesByStatus.size())
                            .page(page + 1)
                            .limit(limit)
                            .build())
                    .build();
        } else {
            // No filters, get all tables with pagination
            tablesPage = tableRepository.findAll(pageable);
            
            List<TableDTO> tableDTOs = tablesPage.getContent().stream()
                    .map(this::convertToDTO)
                    .collect(Collectors.toList());
            
            return PagedResponse.<TableDTO>builder()
                    .content(tableDTOs)
                    .pagination(PagedResponse.PaginationInfo.builder()
                            .total(tablesPage.getTotalElements())
                            .page(page + 1)
                            .limit(limit)
                            .build())
                    .build();
        }
    }

    public TableDTO getTableById(String id) {
        RestaurantTable table = tableRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Không tìm thấy bàn với ID: " + id));
        
        return convertToDTO(table);
    }

    public TableDTO createTable(CreateTableRequest request) {
        // Check if table number already exists
        if (tableRepository.findByTableNumber(request.getTableNumber()).isPresent()) {
            throw new IllegalArgumentException("Số bàn đã tồn tại: " + request.getTableNumber());
        }
        
        RestaurantTable table = RestaurantTable.builder()
                .tableNumber(request.getTableNumber())
                .description(request.getDescription())
                .capacity(request.getCapacity())
                .status(request.getStatus() != null ? request.getStatus() : TableStatus.EMPTY)
                .build();
        
        RestaurantTable savedTable = tableRepository.save(table);
        return convertToDTO(savedTable);
    }

    public TableDTO updateTable(String id, UpdateTableRequest request) {
        RestaurantTable table = tableRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Không tìm thấy bàn với ID: " + id));
        
        // Check if the new table number already exists (only if it's being changed)
        if (request.getTableNumber() != null && !request.getTableNumber().equals(table.getTableNumber())) {
            if (tableRepository.findByTableNumber(request.getTableNumber()).isPresent()) {
                throw new IllegalArgumentException("Số bàn đã tồn tại: " + request.getTableNumber());
            }
            table.setTableNumber(request.getTableNumber());
        }
        
        if (request.getDescription() != null) {
            table.setDescription(request.getDescription());
        }
        
        if (request.getCapacity() != null) {
            table.setCapacity(request.getCapacity());
        }
        
        if (request.getStatus() != null) {
            table.setStatus(request.getStatus());
        }
        
        RestaurantTable updatedTable = tableRepository.save(table);
        return convertToDTO(updatedTable);
    }

    public void deleteTable(String id) {
        RestaurantTable table = tableRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Không tìm thấy bàn với ID: " + id));
        
        // Check if table can be deleted (not OCCUPIED)
        if (table.getStatus() == TableStatus.OCCUPIED) {
            throw new IllegalStateException("Không thể xóa bàn đang có khách");
        }
        
        tableRepository.delete(table);
    }
    
    private TableDTO convertToDTO(RestaurantTable table) {
        return modelMapper.map(table, TableDTO.class);
    }
} 