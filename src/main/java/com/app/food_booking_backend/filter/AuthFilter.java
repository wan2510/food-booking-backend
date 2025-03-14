package com.app.food_booking_backend.filter;

import com.app.food_booking_backend.repository.UserRepository;
import com.app.food_booking_backend.util.JWTUtil;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component
public class AuthFilter extends OncePerRequestFilter {
    private static final Logger logger = LoggerFactory.getLogger(AuthFilter.class);

    private final UserDetailsService userDetailsService;
    private final UserRepository userRepository;

    public AuthFilter(UserDetailsService userDetailsService, UserRepository userRepository) {
        this.userDetailsService = userDetailsService;
        this.userRepository = userRepository;
    }

    @Override
    protected void doFilterInternal(
            HttpServletRequest request,
            HttpServletResponse response,
            FilterChain filterChain
    ) throws ServletException, IOException {
        try {
            // Kiểm tra CORS preflight request
            if (request.getMethod().equals("OPTIONS")) {
                response.setStatus(HttpStatus.OK.value());
                filterChain.doFilter(request, response);
                return;
            }

            String authHeader = request.getHeader("Authorization");
            String token = null;
            String userId = null;
            
            if (authHeader != null && authHeader.startsWith("Bearer ")) {
                token = authHeader.substring(7);
                try {
                    userId = JWTUtil.getUserIdFromToken(token);
                } catch (Exception e) {
                    logger.error("Error extracting user ID from token: {}", e.getMessage());
                }
            }

            logger.debug("Processing request: {} {}", request.getMethod(), request.getRequestURI());
            logger.debug("Auth header present: {}, User ID extracted: {}", authHeader != null, userId);

            if (userId != null && SecurityContextHolder.getContext().getAuthentication() == null) {
                var user = userRepository.findByUuid(userId);
                if (user != null) {
                    UserDetails userDetails = userDetailsService.loadUserByUsername(user.getEmail());
                    if (userDetails != null) {
                        try {
                            if (JWTUtil.validateToken(token)) {
                                UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(
                                    userDetails, 
                                    null, 
                                    userDetails.getAuthorities()
                                );
                                authToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                                SecurityContextHolder.getContext().setAuthentication(authToken);
                                logger.debug("Authentication successful for user: {}", user.getEmail());
                            } else {
                                logger.warn("Invalid token for user: {}", user.getEmail());
                            }
                        } catch (Exception e) {
                            logger.error("Token validation error: {}", e.getMessage());
                        }
                    } else {
                        logger.warn("No user details found for email: {}", user.getEmail());
                    }
                } else {
                    logger.warn("No user found for ID: {}", userId);
                }
            }

            filterChain.doFilter(request, response);
        } catch (Exception e) {
            logger.error("Error in auth filter: {}", e.getMessage());
            response.setStatus(HttpStatus.UNAUTHORIZED.value());
            response.getWriter().write("Unauthorized");
        }
    }
}
