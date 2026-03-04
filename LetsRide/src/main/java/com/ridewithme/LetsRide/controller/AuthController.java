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
    public ResponseEntity<?> register(@RequestBody RegisterRequest request) { // 👈 Use the Record here
        // 1. Manual Debugging - Check your IntelliJ console!
        System.out.println("DEBUG: Incoming Password from JSON: " + request.password());

        if (request.password() == null || request.password().isEmpty()) {
            return ResponseEntity.badRequest().body("Error: Backend received no password!");
        }

        // 2. Check if email exists
        if (userRepository.findByEmail(request.email()).isPresent()) {
            return ResponseEntity.badRequest().body("Error: Email is already in use!");
        }

        // 3. Manually map Record to Entity
        User user = new User();
        user.setName(request.name());
        user.setEmail(request.email());
        user.setRole("USER");

        // 4. Encode the password directly from the Record
        user.setPassword(passwordEncoder.encode(request.password()));

        userRepository.save(user);
        return ResponseEntity.ok("User registered successfully!");
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginRequest request) {
        // 1. Log the incoming data
        System.out.println("DEBUG: Login attempt for email: " + request.email());

        // 2. Find the user
        User user = userRepository.findByEmail(request.email())
                .orElse(null);

        if (user == null) {
            System.out.println("DEBUG: User not found in database for email: " + request.email());
            return ResponseEntity.status(401).body("Invalid credentials");
        }

        // 3. Log the hashes for comparison
        System.out.println("DEBUG: Raw Password from React: " + request.password());
        System.out.println("DEBUG: Encoded Hash in DB: " + user.getPassword());

        // 4. Verify using the PasswordEncoder bean
        boolean isMatch = passwordEncoder.matches(request.password(), user.getPassword());
        System.out.println("DEBUG: Does password match? " + isMatch);

        if (!isMatch) {
            return ResponseEntity.status(401).body("Invalid credentials");
        }

        // 5. Generate and return Token
        String token = jwtUtil.generateToken(user.getEmail());
        return ResponseEntity.ok(new AuthResponse(token));
    }
}

record RegisterRequest(String name, String email, String password) {}
record LoginRequest(String email, String password) {}
record AuthResponse(String token) {}



