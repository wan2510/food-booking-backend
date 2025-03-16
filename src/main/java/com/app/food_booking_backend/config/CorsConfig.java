package com.app.food_booking_backend.config;

import java.util.List;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;

@Configuration
public class CorsConfig {

    @Bean
    public CorsFilter corsFilter() {
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        CorsConfiguration config = new CorsConfiguration();
        
        // Chỉ định origin của frontend
        config.setAllowedOrigins(List.of("http://localhost:5173")); 
        
        // Cho phép các phương thức HTTP
        config.setAllowedMethods(List.of("GET", "POST", "PUT", "DELETE", "OPTIONS"));
        
        // Cho phép tất cả headers
        config.setAllowedHeaders(List.of("*"));

        // Expose các headers cần thiết
        config.setExposedHeaders(List.of("Authorization", "Content-Type"));

        // Cho phép gửi cookie/token
        config.setAllowCredentials(true);
        
        // Thời gian cache preflight request
        config.setMaxAge(3600L);
        
        // Áp dụng cho toàn bộ API
        source.registerCorsConfiguration("/**", config);
        
        return new CorsFilter(source);
    }
}