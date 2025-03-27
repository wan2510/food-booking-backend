package com.app.food_booking_backend.controller;

import java.io.IOException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.app.food_booking_backend.exception.ResourceNotFoundException;
import com.app.food_booking_backend.model.dto.UserDTO;
import com.app.food_booking_backend.model.request.UpdateProfileRequest;
import com.app.food_booking_backend.service.CloudinaryService;
import com.app.food_booking_backend.service.UserService;

@RestController
@RequestMapping("/api/user")
@CrossOrigin(origins = "http://localhost:5173")
public class UserController {

    private static final Logger logger = LoggerFactory.getLogger(UserController.class);
    
    private final UserService userService;
    private final CloudinaryService cloudinaryService;

    public UserController(UserService userService, CloudinaryService cloudinaryService) {
        this.userService = userService;
        this.cloudinaryService = cloudinaryService;
    }

    @GetMapping("/profile")
    public ResponseEntity<UserDTO> getUserProfile() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String email = authentication.getName();
        
        UserDTO userDTO = userService.getUserProfile(email);
        return ResponseEntity.ok(userDTO);
    }

    @PutMapping("/profile")
    public ResponseEntity<UserDTO> updateUserProfile(@RequestBody UpdateProfileRequest updateProfileRequest) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String email = authentication.getName();

        UserDTO updatedUser = userService.updateUserProfile(email, updateProfileRequest);
        return ResponseEntity.ok(updatedUser);
    }
    
    @PostMapping(value = "/profile/avatar", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<?> updateAvatar(@RequestParam("avatar") MultipartFile file) {
        if (file.isEmpty()) {
            return ResponseEntity.badRequest().body("File không được để trống");
        }
        try {
            String imageUrl = cloudinaryService.uploadImage(file);
            
            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
            String email = authentication.getName();
            
            UserDTO updatedUser = userService.updateUserAvatar(email, imageUrl);
            
            return ResponseEntity.ok(updatedUser);
        } catch (IOException e) {
            logger.error("Lỗi khi upload ảnh", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                                 .body("Có lỗi xảy ra khi upload ảnh: " + e.getMessage());
        } catch (ResourceNotFoundException e) {
            logger.error("Không tìm thấy người dùng", e);
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                                 .body("Không tìm thấy người dùng: " + e.getMessage());
        } catch (Exception e) {
            logger.error("Lỗi không xác định khi cập nhật ảnh đại diện", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                                 .body("Có lỗi xảy ra khi cập nhật ảnh đại diện: " + e.getMessage());
        }
    }
}