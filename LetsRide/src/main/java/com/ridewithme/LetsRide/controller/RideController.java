
package com.ridewithme.LetsRide.controller;

import com.ridewithme.LetsRide.security.JwtUtil;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/ride")
@RequiredArgsConstructor
public class RideController {

    private final JwtUtil jwtUtil;

    // 1. This Record acts as a DTO (Data Transfer Object)
    // It must match the keys in your Postman JSON exactly!
    public record RideRequest(String pickupLocation, String destination, String rideType) {}

    @PostMapping("/book")
    public ResponseEntity<?> bookRide(
            @RequestBody RideRequest rideRequest, // 👈 2. This maps the JSON to the Java object
            HttpServletRequest request) {

        // Check for Authorization Header
        String authHeader = request.getHeader("Authorization");
        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            return ResponseEntity.status(401).body("Missing or invalid token");
        }

        String token = authHeader.substring(7);
        String email = jwtUtil.extractEmail(token);

        if (!jwtUtil.validateToken(token, email)) {
            return ResponseEntity.status(401).body("Invalid or expired token");
        }

        // 3. Use the data from the JSON body
        String responseMessage = String.format(
                "Booking confirmed! Type: %s | From: %s | To: %s | User: %s",
                rideRequest.rideType(),
                rideRequest.pickupLocation(),
                rideRequest.destination(),
                email
        );

        return ResponseEntity.ok(responseMessage);
    }
}