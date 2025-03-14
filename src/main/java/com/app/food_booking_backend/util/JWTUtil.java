package com.app.food_booking_backend.util;

import java.security.Key;
import java.util.Date;
import javax.crypto.SecretKey;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;

public class JWTUtil {
    // Use a fixed secret key - in production, this should be in application.properties
    private static final String SECRET_STRING = "404E635266556A586E3272357538782F413F4428472B4B6250645367566B5970";
    private static final Key SECRET = Keys.hmacShaKeyFor(Decoders.BASE64.decode(SECRET_STRING));
    private static final String TOKEN_PREFIX = "Bearer ";
    private static final long EXPIRATION_TIME = 24 * 60 * 60 * 1000; // 24 hours

    public static String generateToken(String userId) {
        Date now = new Date();
        Date expiryDate = new Date(now.getTime() + EXPIRATION_TIME);

        return Jwts.builder()
                .subject(userId)
                .issuedAt(now)
                .expiration(expiryDate)
                .signWith(SECRET)
                .compact();
    }

    public static String getUserIdFromToken(String token) {
        if (token == null) return null;

        try {
            return Jwts.parser()
                    .verifyWith((SecretKey) SECRET)
                    .build()
                    .parseSignedClaims(token)
                    .getPayload()
                    .getSubject();
        } catch (Exception ex) {
            return null;
        }
    }

    public static boolean validateToken(String token) {
        if (token == null) return false;
        
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
