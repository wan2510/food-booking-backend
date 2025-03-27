package com.app.food_booking_backend.service;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import com.app.food_booking_backend.exception.ResourceNotFoundException;
import com.app.food_booking_backend.model.dto.UserDTO;
import com.app.food_booking_backend.model.entity.User;
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
    
    @Transactional
    public UserDTO updateUserAvatar(String email, String avatarUrl) {
        User user = userRepository.findByEmail(email);
        if (user == null) {
            throw new ResourceNotFoundException("Không tìm thấy người dùng với email: " + email);
        }
        
        user.setAvatarUrl(avatarUrl);
        User updatedUser = userRepository.save(user);
        
        UserDTO userDTO = modelMapper.map(updatedUser, UserDTO.class);
        userDTO.setRole(updatedUser.getRole().getName());
        return userDTO;
    }
}