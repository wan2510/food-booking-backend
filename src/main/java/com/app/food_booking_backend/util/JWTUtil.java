package com.app.food_booking_backend.util;

import java.security.Key;
import java.util.Date;

import javax.crypto.SecretKey;

import io.jsonwebtoken.Jwts;

public class JWTUtil {
    // Hằng số dùng chung
    public static final String TOKEN_PREFIX = "Bearer ";

    private static final Key SECRET = Jwts.SIG.HS256.key().build();

    public static String generateToken(String userId) {
        Date now = new Date();
        Date expiryDate = new Date(now.getTime() + 3600000); // 1 giờ

        return Jwts.builder()
                .subject(userId)
                .issuedAt(now)
                .expiration(expiryDate)
                .signWith(SECRET)
                .compact();
    }

    public static String getUserIdFromToken(String token) {
        if (token == null) return null;
        return Jwts.parser()
                .verifyWith((SecretKey) SECRET)
                .build()
                .parseSignedClaims(token)
                .getPayload()
                .getSubject();
    }

    public static boolean validateToken(String token) {
        try {
            Jwts.parser()
                    .verifyWith((SecretKey) SECRET)
                    .build()
                    .parseSignedClaims(token);
            return true;
        } catch (Exception ex) {
            return false;
        }
    }
}
