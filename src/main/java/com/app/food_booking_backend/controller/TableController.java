package com.app.food_booking_backend.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.app.food_booking_backend.exception.ResourceNotFoundException;
import com.app.food_booking_backend.model.dto.TableDTO;
import com.app.food_booking_backend.model.entity.enums.TableStatus;
import com.app.food_booking_backend.model.request.table.CreateTableRequest;
import com.app.food_booking_backend.model.request.table.UpdateTableRequest;
import com.app.food_booking_backend.model.response.ApiResponse;
import com.app.food_booking_backend.model.response.PagedResponse;
import com.app.food_booking_backend.service.TableService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/tables")
@CrossOrigin(origins = "http://localhost:5173", allowCredentials = "true", allowedHeaders = "*")
public class TableController {

    private final TableService tableService;

    public TableController(TableService tableService) {
        this.tableService = tableService;
    }

    @GetMapping
    public ResponseEntity<ApiResponse<PagedResponse<TableDTO>>> getAllTables(
            @RequestParam(defaultValue = "1") int page,
            @RequestParam(defaultValue = "10") int limit,
            @RequestParam(required = false) String search,
            @RequestParam(required = false) TableStatus status) {
        
        PagedResponse<TableDTO> tables = tableService.getAllTables(page, limit, search, status);
        
        return ResponseEntity.ok(ApiResponse.success(tables));
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getTableById(@PathVariable String id) {
        try {
            TableDTO table = tableService.getTableById(id);
            return ResponseEntity.ok(ApiResponse.success(table));
        } catch (ResourceNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(ApiResponse.error(e.getMessage()));
        }
    }

    @PostMapping
    public ResponseEntity<?> createTable(@Valid @RequestBody CreateTableRequest request) {
        try {
            TableDTO createdTable = tableService.createTable(request);
            return ResponseEntity.status(HttpStatus.CREATED)
                    .body(ApiResponse.success(createdTable));
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(ApiResponse.error(e.getMessage()));
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> updateTable(
            @PathVariable String id,
            @Valid @RequestBody UpdateTableRequest request) {
        
        try {
            TableDTO updatedTable = tableService.updateTable(id, request);
            return ResponseEntity.ok(ApiResponse.success(updatedTable));
        } catch (ResourceNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(ApiResponse.error(e.getMessage()));
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(ApiResponse.error(e.getMessage()));
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteTable(@PathVariable String id) {
        try {
            tableService.deleteTable(id);
            return ResponseEntity.ok(ApiResponse.success("Xóa bàn thành công"));
        } catch (ResourceNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(ApiResponse.error(e.getMessage()));
        } catch (IllegalStateException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(ApiResponse.error(e.getMessage()));
        }
    }
} 