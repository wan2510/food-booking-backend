package com.app.food_booking_backend.service;

import com.app.food_booking_backend.model.entity.User;
import com.app.food_booking_backend.model.entity.enums.UserStatusEnum;
import com.app.food_booking_backend.repository.UserRepository;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class UserDetailService implements UserDetailsService {

    private final UserRepository userRepository;

    public UserDetailService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        User user = userRepository.findByEmail(email);
        if (user == null) {
            throw new UsernameNotFoundException("User not found with email: " + email);
        }
        
        List<GrantedAuthority> authorities = new ArrayList<>();
        authorities.add(new SimpleGrantedAuthority(user.getRole().getName()));
        
        return new org.springframework.security.core.userdetails.User(
            user.getEmail(),
            user.getHashPassword(),
            user.getStatus() == UserStatusEnum.ACTIVE,  
            true, 
            true, 
            true, 
            authorities
        );
    }

    private final BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    // Existing methods and fields

    public String encodePassword(String rawPassword) {
        return passwordEncoder.encode(rawPassword);
    }
}
