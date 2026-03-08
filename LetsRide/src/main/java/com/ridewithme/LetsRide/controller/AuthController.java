package com.ridewithme.LetsRide.controller;

import com.ridewithme.LetsRide.model.User;
import com.ridewithme.LetsRide.model.Driver;
import com.ridewithme.LetsRide.repository.UserRepository;
import com.ridewithme.LetsRide.repository.DriverRepository;
import com.ridewithme.LetsRide.security.JwtUtil;
import lombok.RequiredArgsConstructor;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {

    private final UserRepository userRepository;
    private final DriverRepository driverRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtUtil jwtUtil;

    // ================= USER REGISTER =================
    @PostMapping("/register")
    public ResponseEntity<?> register(@RequestBody RegisterRequest request) {

        System.out.println("DEBUG: Incoming Password from JSON: " + request.password());

        if (request.password() == null || request.password().isEmpty()) {
            return ResponseEntity.badRequest().body("Error: Backend received no password!");
        }

        if (userRepository.findByEmail(request.email()).isPresent()) {
            return ResponseEntity.badRequest().body("Error: Email is already in use!");
        }

        User user = new User();
        user.setName(request.name());
        user.setEmail(request.email());
        user.setRole("USER");
        user.setPassword(passwordEncoder.encode(request.password()));

        userRepository.save(user);

        return ResponseEntity.ok("User registered successfully!");
    }

    // ================= LOGIN (USER + DRIVER) =================
    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginRequest request) {

        System.out.println("DEBUG: Login attempt for email: " + request.email());

        // ---------- CHECK USER ----------
        Optional<User> userOptional = userRepository.findByEmail(request.email());

        if (userOptional.isPresent()) {

            User user = userOptional.get();

            if (passwordEncoder.matches(request.password(), user.getPassword())) {

                String token = jwtUtil.generateToken(user.getEmail());
                return ResponseEntity.ok(new AuthResponse(token));

            } else {
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid credentials");
            }
        }

        // ---------- CHECK DRIVER ----------
        Optional<Driver> driverOptional = driverRepository.findByEmail(request.email());

        if (driverOptional.isPresent()) {

            Driver driver = driverOptional.get();

            if (passwordEncoder.matches(request.password(), driver.getPassword())) {

                String token = jwtUtil.generateToken(driver.getEmail());
                return ResponseEntity.ok(new AuthResponse(token));

            } else {
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid credentials");
            }
        }

        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("User not found");
    }
}

// ================= REQUEST / RESPONSE =================

record RegisterRequest(String name, String email, String password) {}

record LoginRequest(String email, String password) {}

record AuthResponse(String token) {}