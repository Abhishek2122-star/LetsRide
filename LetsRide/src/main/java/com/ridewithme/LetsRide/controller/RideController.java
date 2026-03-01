package com.ridewithme.LetsRide.controller;

import com.ridewithme.LetsRide.model.Ride;
import com.ridewithme.LetsRide.model.User;
import com.ridewithme.LetsRide.repository.UserRepository;
import com.ridewithme.LetsRide.service.RideService;
import com.ridewithme.LetsRide.security.JwtUtil;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/ride")
@RequiredArgsConstructor
public class RideController {

    private final JwtUtil jwtUtil;
    private final RideService rideService;
    private final UserRepository userRepository;

    // Matches what React sends in the JSON body
    public record RideRequest(String pickupLocation, String destination, String rideType) {}

    @PostMapping("/book")
    public ResponseEntity<?> bookRide(@RequestBody RideRequest rideRequest, HttpServletRequest request) {
        String authHeader = request.getHeader("Authorization");
        String token = authHeader.substring(7);
        String email = jwtUtil.extractEmail(token);

        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("User not found"));

        // ✅ Fixed: Now passing 4 arguments: ID, Pickup, Drop (destination), and Type
        Ride ride = rideService.requestRide(
                user.getId(),
                rideRequest.pickupLocation(),
                rideRequest.destination(),
                rideRequest.rideType()
        );

        return ResponseEntity.ok(ride);
    }

    // ✅ ADD THIS: This is what your React Dashboard "api.get('/ride/all')" calls!
    @GetMapping("/all")
    public ResponseEntity<List<Ride>> getAllRides() {
        return ResponseEntity.ok(rideService.getAllRides());
    }

    @GetMapping("/my-rides")
    public ResponseEntity<List<Ride>> getMyRides(HttpServletRequest request) {
        String authHeader = request.getHeader("Authorization");
        String token = authHeader.substring(7);
        String email = jwtUtil.extractEmail(token);

        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("User not found"));

        return ResponseEntity.ok(rideService.getRidesByUser(user.getId()));
    }
}