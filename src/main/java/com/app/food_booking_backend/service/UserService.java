package com.app.food_booking_backend.service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.app.food_booking_backend.exception.ResourceNotFoundException;
import com.app.food_booking_backend.model.dto.UserDTO;
import com.app.food_booking_backend.model.entity.User;
import com.app.food_booking_backend.model.request.UpdateProfileRequest;
import com.app.food_booking_backend.repository.RoleRepository;
import com.app.food_booking_backend.repository.UserRepository;

import jakarta.transaction.Transactional;

@Service
public class UserService {

    private final UserRepository userRepository;
    private final ModelMapper modelMapper;
    private final PasswordEncoder passwordEncoder;
    private final RoleRepository roleRepository;

    public UserService(UserRepository userRepository, ModelMapper modelMapper, PasswordEncoder passwordEncoder, RoleRepository roleRepository) {
        this.userRepository = userRepository;
        this.modelMapper = modelMapper;
        this.passwordEncoder = passwordEncoder;
        this.roleRepository = roleRepository;
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
    private User toUser(UserDTO userDTO) {
        if (userDTO == null) {
            throw new IllegalArgumentException("Không nhận được thông tin user!");
        }
        User user = modelMapper.map(userDTO, User.class);
        // Chỉ băm nếu hashPassword là plaintext và không phải chuỗi băm cũ
        if (userDTO.getHashPassword() != null && !userDTO.getHashPassword().startsWith("$2a$")) {
            user.setHashPassword(passwordEncoder.encode(userDTO.getHashPassword()));
        } else {
            user.setHashPassword(userDTO.getHashPassword()); // Giữ nguyên nếu đã là chuỗi băm
        }
        user.setRole(roleRepository.findByName(userDTO.getRole()));
        return user;
    }

    private UserDTO toUserDTO(User user) {
        if (user == null) {
            throw new IllegalArgumentException("Thông tin User không nhận diện được!");
        }

        UserDTO userDTO = modelMapper.map(user, UserDTO.class);
        userDTO.setRole(user.getRole().getName());
        return userDTO;
    }

    // Lấy danh sách user chưa bị xóa mềm (status != 'DELETED')
    public List<UserDTO> getUsers() {
        try {
            return userRepository.findActiveUsers().stream()
                    .map(this::toUserDTO)
                    .collect(Collectors.toList());
        } catch (Exception e) {
            throw new RuntimeException("Lỗi khi lấy danh sách account: " + e.getMessage());
        }
    }

    // Thêm tài khoản mới
    public User createAccount(UserDTO userDTO) {
        try {
            System.out.println("\n\n\n\n\n =============VÀO CREATE VỚI DATA DTO =============" + userDTO.toString());
            if (userRepository.findByEmail(userDTO.getEmail()) != null) {
                throw new RuntimeException("Email đã tồn tại!");
            }
            if (userRepository.findByUuid(userDTO.getUuid()) != null) {
                throw new RuntimeException("UUID đã tồn tại!");
            }
            User user = toUser(userDTO);
            System.out.println("\n\n\n\n\n ============= ĐÃ UPDATE VỚI DATA  =============\n" + user.getRole().getName() +"\n"+ user.getPhone() +"\n"+ user.getStatus() +"\n"+ user.getFullName());
            return userRepository.save(user);
        } catch (Exception e) {
            throw new RuntimeException("Lỗi khi thêm user: " + e.getMessage());
        }
    }

    public User updateAccount(UserDTO userDTO) {
        System.out.println("\n\n\n\n\n ============= VÀO UPDATE VỚI DATA =============\n" + userDTO.toString());
        try {

            Optional<User> existingUser = userRepository.findById(userDTO.getUuid());
            if (!existingUser.isPresent()) {
                throw new RuntimeException("Không tìm thấy user với id: " + userDTO.getUuid());
            }else{
                User updateUser = existingUser.get();
                User newInfo = toUser(userDTO);
                modelMapper.map(updateUser, newInfo);
                System.out.println("\n\n\n\n\n ============= ĐÃ UPDATE VỚI DATA  =============\n" +"\n"+ updateUser.getRole() +"\n"+ updateUser.getPhone() +"\n"+ updateUser.getStatus() +"\n"+ updateUser.getFullName());
                return userRepository.save(toUser(userDTO));
            }
        } catch (Exception e) {
            throw new RuntimeException("Lỗi khi cập nhật user: " + e.getMessage());
        }
    }
}
