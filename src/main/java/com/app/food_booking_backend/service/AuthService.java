package com.app.food_booking_backend.service;

import com.app.food_booking_backend.exception.InvalidEmailException;
import com.app.food_booking_backend.exception.UnauthorizedException;
import com.app.food_booking_backend.model.dto.UserDTO;
import com.app.food_booking_backend.model.entity.Role;
import com.app.food_booking_backend.model.entity.User;
import com.app.food_booking_backend.model.entity.enums.UserStatusEnum;
import com.app.food_booking_backend.model.request.LoginRequest;
import com.app.food_booking_backend.model.request.RegisterRequest;
import com.app.food_booking_backend.model.response.LoginResponse;
import com.app.food_booking_backend.repository.RoleRepository;
import com.app.food_booking_backend.repository.UserRepository;
import com.app.food_booking_backend.util.JWTUtil;
import com.app.food_booking_backend.util.Validation;
import jakarta.transaction.Transactional;
import org.modelmapper.ModelMapper;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AuthService {

    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;
    private final ModelMapper modelMapper;

    public AuthService(
            UserRepository userRepository,
            RoleRepository roleRepository,
            PasswordEncoder passwordEncoder,
            AuthenticationManager authenticationManager,
            ModelMapper modelMapper
    ) {
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
        this.passwordEncoder = passwordEncoder;
        this.authenticationManager = authenticationManager;
        this.modelMapper = modelMapper;
    }

    public LoginResponse login(LoginRequest loginRequest) {
        Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(
                loginRequest.getEmail(), loginRequest.getPassword()));
        if (!authentication.isAuthenticated()) {
            throw new UnauthorizedException("Invalid email or password");
        }
        User user = userRepository.findByEmail(loginRequest.getEmail());
        String accessToken = JWTUtil.generateToken(user.getUuid());
        return LoginResponse.builder()
                .user(modelMapper.map(user, UserDTO.class))
                .accessToken(accessToken)
                .build();
    }

    @Transactional
    public User register(RegisterRequest registerRequest) {
        if (Validation.isValidEmail(registerRequest.getEmail())) {
            throw new InvalidEmailException("Invalid email");
        }
        User user = userRepository.findByEmail(registerRequest.getEmail());
        if (user != null) {
            throw new InvalidEmailException("Email already in use");
        }
        Role role = roleRepository.findByName("USER");
        User newUser = User.builder()
                .email(registerRequest.getEmail())
                .phone(registerRequest.getPhone())
                .fullName(registerRequest.getFullName())
                .status(UserStatusEnum.ACTIVE)
                .hashPassword(passwordEncoder.encode(registerRequest.getPassword()))
                .role(role)
                .build();
        return userRepository.save(newUser);
    }
}
