package com.app.food_booking_backend.service;

import com.app.food_booking_backend.model.entity.Combo;
import com.app.food_booking_backend.model.entity.Food;
import com.app.food_booking_backend.repository.ComboRepository;
import com.app.food_booking_backend.repository.FoodRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class ComboService {

    @Autowired
    private ComboRepository comboRepository;

    @Autowired
    private FoodRepository foodRepository;

    public List<Combo> getAllCombos() {
        return comboRepository.findAll();
    }

    public Combo createCombo(String name, List<String> foodIds, BigDecimal totalPrice, String description, String imageUrl, String status) {
        List<Food> foods = foodRepository.findAllById(foodIds);
        Combo combo = Combo.builder()
            .uuid(UUID.randomUUID().toString()) // Generate UUID
            .name(name)
            .totalPrice(totalPrice)
            .description(description)
            .imageUrl(imageUrl)
            .status(status)
            .foods(foods)
            .build();
        return comboRepository.save(combo);
    }

    public void deleteCombo(String uuid) {
        comboRepository.deleteById((uuid));
    }

    public Combo updateCombo(String uuid, String name, List<String> foodIds, BigDecimal totalPrice, String description, String imageUrl, String status) {
        Optional<Combo> optionalCombo = comboRepository.findById(uuid);
        if (optionalCombo.isPresent()) {
            Combo combo = optionalCombo.get();
            combo.setName(name);
            combo.setTotalPrice(totalPrice);
            combo.setDescription(description);
            combo.setImageUrl(imageUrl);
            combo.setStatus(status);
            List<Food> foods = foodRepository.findAllById(foodIds);
            combo.setFoods(foods);
            return comboRepository.save(combo);
        } else {
            throw new RuntimeException("Combo not found with uuid: " + uuid);
        }
    }
}