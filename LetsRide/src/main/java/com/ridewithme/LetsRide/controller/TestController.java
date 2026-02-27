package com.ridewithme.LetsRide.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TestController {

    // Public endpoint
    @GetMapping("/")
    public String home() {
        return "Backend is running 🚀";
    }

    // Protected endpoint
    @GetMapping("/secure")
    public String secure() {
        return "Secure API accessed 🔐";
    }
}