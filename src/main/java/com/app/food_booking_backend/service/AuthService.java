package com.app.food_booking_backend.service;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;

import org.modelmapper.ModelMapper;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

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

@Service
public class AuthService {

    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;
    private final ModelMapper modelMapper;
    private final EmailService emailService;
    private final Map<String, String> otpStorage = new HashMap<>();
    private final Map<String, Long> otpExpiry = new HashMap<>();
    
    private static final long OTP_EXPIRY_DURATION = 5 * 60 * 1000;
    
    public AuthService(
            UserRepository userRepository,
            RoleRepository roleRepository,
            PasswordEncoder passwordEncoder,
            AuthenticationManager authenticationManager,
            ModelMapper modelMapper,
            EmailService emailService
    ) {
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
        this.passwordEncoder = passwordEncoder;
        this.authenticationManager = authenticationManager;
        this.modelMapper = modelMapper;
        this.emailService = emailService;
    }

    public LoginResponse login(LoginRequest loginRequest) {
        Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(
                loginRequest.getEmail(), loginRequest.getPassword()));
        if (!authentication.isAuthenticated()) {
            throw new UnauthorizedException("Invalid email or password");
        }
        User user = userRepository.findByEmail(loginRequest.getEmail());
        UserDTO userDTO = modelMapper.map(user, UserDTO.class);
        userDTO.setRole(user.getRole().getName());
        String accessToken = JWTUtil.generateToken(user.getEmail());
        return LoginResponse.builder()
                .user(userDTO)
                .accessToken(accessToken)
                .build();
    }

    @Transactional
    public User register(RegisterRequest registerRequest) {
        if (!Validation.isValidEmail(registerRequest.getEmail())) {
            throw new InvalidEmailException("Invalid email");
        }
        User user = userRepository.findByEmail(registerRequest.getEmail());
        if (user != null) {
            throw new InvalidEmailException("Email already in use");
        }
        Role role = roleRepository.findByName("ROLE_USER");
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

    public void sendOTP(String email) {
        String otp = generateOTP();
        otpStorage.put(email, otp);
        otpExpiry.put(email, System.currentTimeMillis() + OTP_EXPIRY_DURATION);

        String subject = "Mã xác thực OTP của bạn";
        String message = "Mã OTP của bạn là: " + otp + ". Vui lòng nhập mã này để tiếp tục.\n"
                       + "Lưu ý: Mã này sẽ hết hạn sau 5 phút.";
        emailService.sendEmail(email, subject, message);
    }

    public boolean verifyOTP(String email, String otp) {
        String storedOTP = otpStorage.get(email);
        Long expiryTime = otpExpiry.get(email);

        if (storedOTP == null || expiryTime == null || !storedOTP.equals(otp)) {
            return false;
        }

        if (System.currentTimeMillis() > expiryTime) {
            otpStorage.remove(email);
            otpExpiry.remove(email);
            return false;
        }

        otpStorage.remove(email);
        otpExpiry.remove(email);
        return true;
    }

    private String generateOTP() {
        Random random = new Random();
        int otp = 100000 + random.nextInt(999999);
        return String.valueOf(otp);
    }
}