package com.ridewithme.LetsRide.security;

import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.util.Date;

@Component
public class JwtUtil {

    // ✅ MUST be >= 32 characters
    private final String SECRET = "LetsRideSecretKeyForJwtTokenGeneration2026";

    // 🔐 Secure key
    private final SecretKey key = Keys.hmacShaKeyFor(SECRET.getBytes());

    private final long EXPIRATION = 86400000; // 1 day

    // 🔐 Generate JWT token
    public String generateToken(String email) {

        return Jwts.builder()
                .setSubject(email)
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + EXPIRATION))
                .signWith(key, SignatureAlgorithm.HS256)
                .compact();
    }

    // 📖 Extract email from token
    public String extractEmail(String token) {

        return extractClaims(token).getSubject();
    }

    // 📖 Extract all claims
    private Claims extractClaims(String token) {

        return Jwts.parserBuilder()
                .setSigningKey(key)
                .build()
                .parseClaimsJws(token)
                .getBody();
    }

    // ⏰ Check expiration
    private boolean isTokenExpired(String token) {

        return extractClaims(token)
                .getExpiration()
                .before(new Date());
    }

    // ✅ Validate token
    public boolean validateToken(String token, String email) {

        try {
            String extractedEmail = extractEmail(token);

            return extractedEmail.equals(email)
                    && !isTokenExpired(token);

        } catch (JwtException e) {
            return false;
        }
    }
}