package com.app.food_booking_backend.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.app.food_booking_backend.model.dto.StaffDTO;
import com.app.food_booking_backend.repository.StaffRepository;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class StaffService {

    @Autowired
    private StaffRepository staffRepository;

    public List<StaffDTO> getAllStaff() {
        return staffRepository.findAll().stream().map(staff -> {
            StaffDTO dto = new StaffDTO();
            dto.setUuid(staff.getId());
            dto.setFullName(staff.getName());
            dto.setEmail(staff.getEmail());
            dto.setPhone(staff.getPhone());
            return dto;
        }).collect(Collectors.toList());
    }
}
