package com.app.food_booking_backend.util;

import io.jsonwebtoken.Jwts;

import javax.crypto.SecretKey;
import java.security.Key;
import java.util.Date;

public class JWTUtil {
    private static final Key SECRET = Jwts.SIG.HS256.key().build();
    private static final String TOKEN_PREFIX = "Bearer ";

    public static String generateToken(String userId) {
        Date now = new Date();
        Date expiryDate = new Date(now.getTime() + 3600000);

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
