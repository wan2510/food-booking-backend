package com.app.food_booking_backend.controller;

import com.app.food_booking_backend.model.entity.Combo;
import com.app.food_booking_backend.service.ComboService;
import com.app.food_booking_backend.repository.ComboRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.*;

@RestController
@RequestMapping("/api/combos")
@CrossOrigin(origins = "http://localhost:5173", methods = {RequestMethod.GET, RequestMethod.POST, RequestMethod.PUT, RequestMethod.DELETE})
public class ComboController {

    private static final Logger logger = LoggerFactory.getLogger(ComboController.class);
    @Autowired
    private ComboService comboService;

    @Autowired
    private ComboRepository comboRepository;

    @GetMapping
    public ResponseEntity<List<Combo>> getAllCombos() {
        try {
            logger.info("Fetching all combos");
            List<Combo> combos = comboService.getAllCombos();
            return ResponseEntity.ok(combos);
        } catch (Exception e) {
            logger.error("Error fetching combos: {}", e.getMessage(), e);
            return ResponseEntity.badRequest().build();
        }
    }

    @PostMapping
    public ResponseEntity<Combo> createCombo(@RequestBody Map<String, Object> request) {
        try {
            String name = (String) request.get("name");
            List<String> foodIds = (List<String>) request.get("foodIds");
            BigDecimal totalPrice = new BigDecimal(request.get("totalPrice").toString());
            String description = (String) request.get("description");
            String image = (String) request.get("image");
            String status = (String) request.get("status");

            Combo combo = comboService.createCombo(name, foodIds, totalPrice, description, image, status);
            return ResponseEntity.ok(combo);
        } catch (Exception e) {
            logger.error("Error creating combo: {}", e.getMessage(), e);
            return ResponseEntity.badRequest().build();
        }
    }



    @DeleteMapping("/{uuid}")
public ResponseEntity<Void> deleteCombo(@PathVariable String uuid) {
    try {
        if (uuid == null || uuid.equals("undefined")) {
            logger.error("Invalid UUID: {}", uuid);
            return ResponseEntity.badRequest().build();
        }
        Optional<Combo> combo = comboRepository.findById(uuid);
        if (!combo.isPresent()) {
            logger.error("Combo not found with UUID: {}", uuid);
            return ResponseEntity.notFound().build();
        }
        comboService.deleteCombo(uuid);
        logger.info("Combo deleted successfully: {}", uuid);
        return ResponseEntity.noContent().build();
    } catch (Exception e) {
        logger.error("Error deleting combo with UUID {}: {}", uuid, e.getMessage(), e);
        return ResponseEntity.badRequest().build();
    }
}

@PutMapping("/{uuid}")
    public ResponseEntity<Void> updateCombo(@PathVariable String uuid, @RequestBody Map<String, Object> request) {
        try {
            if (uuid == null || uuid.equals("undefined")) {
                logger.error("Invalid UUID: {}", uuid);
                return ResponseEntity.badRequest().build();
            }
            Optional<Combo> combo = comboRepository.findById(uuid);
            if (!combo.isPresent()) {
                logger.error("Combo not found with UUID: {}", uuid);
                return ResponseEntity.notFound().build();
            }
            String name = (String) request.get("name");
            List<String> foodIds = (List<String>) request.get("foodIds");
            BigDecimal totalPrice = new BigDecimal(request.get("totalPrice").toString());
            String description = (String) request.get("description");
            String image = (String) request.get("image");
            String status = (String) request.get("status");

            comboService.updateCombo(uuid, name, foodIds, totalPrice, description, image, status);
            logger.info("Combo updated successfully: {}", uuid);
            return ResponseEntity.noContent().build();
        } catch (Exception e) {
            logger.error("Error updating combo with UUID {}: {}", uuid, e.getMessage(), e);
            return ResponseEntity.badRequest().build();
        }
    }
}