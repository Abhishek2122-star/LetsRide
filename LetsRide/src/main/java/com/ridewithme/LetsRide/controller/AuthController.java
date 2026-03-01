package com.ridewithme.LetsRide.controller;

import com.ridewithme.LetsRide.model.User;
import com.ridewithme.LetsRide.repository.UserRepository;
import com.ridewithme.LetsRide.security.JwtUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtUtil jwtUtil;

    @PostMapping("/register")
    public ResponseEntity<?> register(@RequestBody RegisterRequest request) {
        if (userRepository.findByEmail(request.email()).isPresent()) {
            return ResponseEntity.badRequest().body("Email already exists");
        }

        User user = new User();
        user.setName(request.name());
        user.setEmail(request.email());
        user.setPassword(passwordEncoder.encode(request.password()));
        user.setRole("USER");

        userRepository.save(user);
        return ResponseEntity.ok("User registered successfully");
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginRequest request) {
        // 1. Log what is coming from the React Frontend
        System.out.println("DEBUG: Login attempt for email: " + request.email());

        User user = userRepository.findByEmail(request.email())
                .orElseThrow(() -> new RuntimeException("User not found"));

        // 2. Log what is stored in the Database (The Hash)
        System.out.println("DEBUG: Stored Hash in DB: " + user.getPassword());

        // 3. Check if they match
        boolean isMatch = passwordEncoder.matches(request.password(), user.getPassword());
        System.out.println("DEBUG: Password Match Result: " + isMatch);

        if (!isMatch) {
            return ResponseEntity.status(401).body("Invalid credentials");
        }

        String token = jwtUtil.generateToken(user.getEmail());
        return ResponseEntity.ok(new AuthResponse(token));
    }
}

record RegisterRequest(String name, String email, String password) {}
record LoginRequest(String email, String password) {}
record AuthResponse(String token) {}