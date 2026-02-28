package com.ridewithme.LetsRide.controller;

import com.ridewithme.LetsRide.model.User;
import com.ridewithme.LetsRide.service.UserService;
import lombok.RequiredArgsConstructor; // Use Lombok to keep it clean
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/users")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    // ❌ REMOVED: @PostMapping("/register")
    // This is now handled by AuthController at /auth/register

    // Get all users (This will now require a JWT Token to access)
    @GetMapping
    public List<User> getUsers() {
        return userService.getAllUsers();
    }

    // You can add "Get Profile" or "Update Profile" here later
}