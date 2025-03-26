package com.app.food_booking_backend.service;


import com.app.food_booking_backend.model.dto.UserDTO;
import com.app.food_booking_backend.model.entity.Role;
import com.app.food_booking_backend.model.entity.User;
import com.app.food_booking_backend.model.entity.enums.UserStatusEnum;
import com.app.food_booking_backend.repository.RoleRepository;
import com.app.food_booking_backend.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.app.food_booking_backend.service.UserDetailService;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RoleRepository roleRepository;
    @Autowired
    private UserDetailService userDetailService;

    public List<UserDTO> getAllStaff() {
        return userRepository.findAllStaff().stream().map(this::convertToDTO).collect(Collectors.toList());
    }

    public User findByUuid(String uuid) {
        return userRepository.findById(uuid).orElse(null);
    }

    // Lấy danh sách nhân viên theo trạng thái
    public List<UserDTO> getStaffByStatus(String status) {
        return userRepository.findByStatus(status).stream()
                .filter(user -> List.of("3", "4", "5", "6").contains(user.getRole().getUuid()))
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    // Tìm kiếm nhân viên theo từ khóa
    public List<UserDTO> searchStaff(String keyword) {
        return userRepository.searchByKeyword(keyword).stream()
                .filter(user -> List.of("3", "4", "5", "6").contains(user.getRole().getUuid()))
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    // Tạo mới nhân viên
    public UserDTO createStaff(UserDTO userDTO) {
    User user = new User();
    user.setUuid(userDTO.getUuid() != null ? userDTO.getUuid() : UUID.randomUUID().toString());
    user.setEmail(userDTO.getEmail());
    user.setHashPassword(userDetailService.encodePassword("default_password")); // Sử dụng logic mã hóa mật khẩu
    user.setFullName(userDTO.getFullName());
    user.setPhone(userDTO.getPhone());
    
    // Chuyển đổi status từ String sang UserStatusEnum
    user.setStatus(userDTO.getStatus() != null ? UserStatusEnum.valueOf(userDTO.getStatus()) : UserStatusEnum.ACTIVE);
    
    user.setRole(roleRepository.findById(userDTO.getRoleId())
            .orElseThrow(() -> new RuntimeException("Role not found")));
    user.setStartDate(userDTO.getStartDate());
    user.setNote(userDTO.getNote());
    user = userRepository.save(user);
    return convertToDTO(user);
}

    // Cập nhật thông tin nhân viên
    public UserDTO updateStaff(String uuid, UserDTO userDTO) {
        User user = userRepository.findById(uuid)
                .orElseThrow(() -> new RuntimeException("User not found"));
        user.setEmail(userDTO.getEmail());
        user.setFullName(userDTO.getFullName());
        user.setPhone(userDTO.getPhone());
        user.setStatus(userDTO.getStatus() != null ? UserStatusEnum.valueOf(userDTO.getStatus()) : UserStatusEnum.ACTIVE);
        user.setRole(roleRepository.findById(userDTO.getRoleId())
                .orElseThrow(() -> new RuntimeException("Role not found")));
        user.setStartDate(userDTO.getStartDate());
        user.setNote(userDTO.getNote());
        user = userRepository.save(user);
        return convertToDTO(user);
    }

    // Xóa nhân viên
    public void deleteStaff(String uuid) {
        userRepository.deleteById(uuid);
    }

    // Chuyển đổi từ User entity sang UserDTO
    private UserDTO convertToDTO(User user) {
        UserDTO dto = new UserDTO();
        dto.setUuid(user.getUuid());
        dto.setEmail(user.getEmail());
        dto.setFullName(user.getFullName());
        dto.setPhone(user.getPhone());
        dto.setStatus(user.getStatus().name());
        dto.setRoleId(user.getRole().getUuid());
        dto.setRoleName(user.getRole().getName());
        dto.setStartDate(user.getStartDate());
        dto.setNote(user.getNote());
        dto.setCreatedAt(user.getCreatedAt());
        return dto;
    }
}