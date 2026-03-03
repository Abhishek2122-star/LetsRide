package com.ridewithme.LetsRide.security;

import io.jsonwebtoken.*;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.security.Key;
import java.util.Date;

@Component
public class JwtUtil {

    // ✅ Requirement: Must be at least 256-bit (32+ chars)
    private final String SECRET_STRING = "LetsRideSecretKeyForJwtTokenGeneration2026_Secure_Long_String";

    // ✅ Initialize the key once to be used for both Signing and Parsing
    private final SecretKey key = Keys.hmacShaKeyFor(SECRET_STRING.getBytes());

    // ✅ 7 Days Expiration
    private final long EXPIRATION_TIME = 604800000;

    // 🚀 1. Generate JWT token
    public String generateToken(String email) {
        return Jwts.builder()
                .setSubject(email)
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + EXPIRATION_TIME))
                .signWith(key, SignatureAlgorithm.HS256) // ✅ Uses the initialized key
                .compact();
    }

    // 🚀 2. Extract Email (Subject)
    public String extractEmail(String token) {
        return extractClaims(token).getSubject();
    }

    // 🚀 3. Extract All Claims (Parsing)
    private Claims extractClaims(String token) {
        return Jwts.parserBuilder()
                .setSigningKey(key) // ✅ Uses the same key to verify
                .build()
                .parseClaimsJws(token)
                .getBody();
    }

    // 🚀 4. Check if token is expired
    private boolean isTokenExpired(String token) {
        return extractClaims(token)
                .getExpiration()
                .before(new Date());
    }

    // 🚀 5. Validate Token
    public boolean validateToken(String token, String email) {
        try {
            final String extractedEmail = extractEmail(token);
            return (extractedEmail.equals(email) && !isTokenExpired(token));
        } catch (ExpiredJwtException e) {
            System.out.println("❌ Token Expired: " + e.getMessage());
            return false;
        } catch (JwtException | IllegalArgumentException e) {
            System.out.println("❌ Invalid Token: " + e.getMessage());
            return false;
        }
    }
}