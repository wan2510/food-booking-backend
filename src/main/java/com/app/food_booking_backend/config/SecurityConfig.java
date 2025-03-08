package com.app.food_booking_backend.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import com.app.food_booking_backend.filter.AuthFilter;
import com.app.food_booking_backend.service.UserDetailService;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    private final UserDetailService userDetailService;
    private final AuthFilter authFilter;

    public SecurityConfig(UserDetailService userDetailService, AuthFilter authFilter) {
        this.userDetailService = userDetailService;
        this.authFilter = authFilter;
    }

    @Bean
public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
    return http
            .csrf(AbstractHttpConfigurer::disable)
            .authorizeHttpRequests((authorize) ->
                    authorize
                            .requestMatchers(
                                    "/api/auth/**",
                                    "/api/email/**"
                            )
                            .permitAll()
                            .anyRequest().authenticated()
            )
            .addFilterBefore(authFilter, UsernamePasswordAuthenticationFilter.class)
            .build();
}
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration configuration) throws Exception {
        return configuration.getAuthenticationManager();
    }

    @Bean
    public AuthenticationProvider authenticationProvider() {
        DaoAuthenticationProvider authenticationProvider = new DaoAuthenticationProvider();
        authenticationProvider.setUserDetailsService(userDetailService);
        authenticationProvider.setPasswordEncoder(passwordEncoder());
        return authenticationProvider;
    }
}