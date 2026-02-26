package com.ridewithme.LetsRide.service;

import com.ridewithme.LetsRide.model.User;
import com.ridewithme.LetsRide.repository.UserRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public UserService(UserRepository userRepository,
                       PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    // Register user
    public User registerUser(User user) {

        // Encrypt password
        user.setPassword(passwordEncoder.encode(user.getPassword()));

        // Set default role
        user.setRole("USER");

        return userRepository.save(user);
    }

    // Login user
    public User loginUser(String email, String password) {

        User user = userRepository.findByEmail(email);

        if (user == null) {
            throw new RuntimeException("User not found");
        }

        // Check encrypted password
        if (!passwordEncoder.matches(password, user.getPassword())) {
            throw new RuntimeException("Invalid password");
        }

        return user;
    }

    // Get all users
    public List<User> getAllUsers() {
        return userRepository.findAll();
    }
}