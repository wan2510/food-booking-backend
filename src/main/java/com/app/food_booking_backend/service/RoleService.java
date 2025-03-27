package com.app.food_booking_backend.service;

import org.springframework.stereotype.Service;
import java.util.List;

import com.app.food_booking_backend.model.entity.Role;
import com.app.food_booking_backend.repository.RoleRepository;

@Service
public class RoleService {

    private final RoleRepository roleRepository;

    public RoleService(RoleRepository roleRepository) {
        this.roleRepository = roleRepository;
    }

    // Lấy danh sách tất cả vai trò
    public List<Role> getAllRoles() {
        try {
            return roleRepository.findAll();
        } catch (Exception e) {
            throw new RuntimeException("Lỗi khi lấy danh sách vai trò: " + e.getMessage());
        }
    }
}