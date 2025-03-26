package com.app.food_booking_backend.service;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collector;
import java.util.stream.Collectors;

import com.app.food_booking_backend.exception.ResourceNotFoundException;
import com.app.food_booking_backend.model.dto.UserDTO;
import com.app.food_booking_backend.model.dto.VoucherDTO;
import com.app.food_booking_backend.model.entity.User;
import com.app.food_booking_backend.model.entity.Voucher;
import com.app.food_booking_backend.model.request.UpdateProfileRequest;
import com.app.food_booking_backend.repository.UserRepository;

import jakarta.transaction.Transactional;

@Service
public class UserService {

    private final UserRepository userRepository;
    private final ModelMapper modelMapper;

    
    public UserService(UserRepository userRepository, ModelMapper modelMapper) {
        this.userRepository = userRepository;
        this.modelMapper = modelMapper;
    }

    // Chuyển từ UserDTO sang User (entity)
    private User toUser(UserDTO userDTO) {
        // Kiểm tra null cho UserDTO
        if (userDTO == null) {
            throw new IllegalArgumentException("Không nhận được thông tin user!");
        }
        User user = modelMapper.map(userDTO, User.class);
        return user;
    }

    // Chuyển từ User (entity) sang UserDTO
    private UserDTO toUserDTO(User user) {
        // Kiểm tra null
        if (user == null) {
            throw new IllegalArgumentException("Thông tin User không nhận diện được!");
        }
        return modelMapper.map(user, UserDTO.class);
    }

    // Lấy danh sách tất cả user từ DB và chuyển thành DTO
    public List<UserDTO> getUsers(){
        try{
            return userRepository.findAll().stream()
            .map(this::toUserDTO)
            .collect(Collectors.toList());
        }catch(Exception e){
            throw new RuntimeException("Lỗi khi lấy danh sách account: " + e.getMessage());
        }
    }

     // Thêm một tài khoản Staff mới vào DB
     public User createAccountStaff(UserDTO userDTO) {
        try {
            User user = toUser(userDTO);
            return userRepository.save(user);
        } catch (Exception e) {
            throw new RuntimeException("Lỗi khi thêm user: " + e.getMessage());
        }
    }

    // Cập nhật thông tin một user
    public User updateAccountUser(UserDTO userDTO) {
        try {
            Optional<User> existingUser = userRepository.findById(userDTO.getUuid());
            if (existingUser.isPresent()) {
                User user = existingUser.get();
                modelMapper.map(userDTO, user);
                return userRepository.save(user);
            } else {
                throw new RuntimeException("Không tìm thấy user với id: " + userDTO.getUuid());
            }
        } catch (Exception e) {
            throw new RuntimeException("Lỗi khi cập nhật user: " + e.getMessage());
        }
    }

    public UserDTO getUserProfile(String email) {
        User user = userRepository.findByEmail(email);
        if (user == null) {
            throw new ResourceNotFoundException("Không tìm thấy người dùng với email: " + email);
        }

        UserDTO userDTO = modelMapper.map(user, UserDTO.class);
        userDTO.setRole(user.getRole().getName());
        return userDTO;
    }

    @Transactional
    public UserDTO updateUserProfile(String email, UpdateProfileRequest updateProfileRequest) {
        User user = userRepository.findByEmail(email);
        if (user == null) {
            throw new ResourceNotFoundException("Không tìm thấy người dùng với email: " + email);
        }

        // Cập nhật thông tin người dùng
        if (updateProfileRequest.getFullName() != null) {
            user.setFullName(updateProfileRequest.getFullName());
        }
        if (updateProfileRequest.getPhone() != null) {
            user.setPhone(updateProfileRequest.getPhone());
        }

        User updatedUser = userRepository.save(user);

        UserDTO userDTO = modelMapper.map(updatedUser, UserDTO.class);
        userDTO.setRole(updatedUser.getRole().getName());
        return userDTO;
    }
}
