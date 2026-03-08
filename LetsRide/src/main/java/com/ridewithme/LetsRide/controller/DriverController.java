package com.ridewithme.LetsRide.controller;

import com.ridewithme.LetsRide.model.Driver;
import com.ridewithme.LetsRide.repository.DriverRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/drivers")
@RequiredArgsConstructor
public class DriverController {

    private final DriverRepository driverRepository;
    private final PasswordEncoder passwordEncoder;

    // URL: http://localhost:8080/drivers/register
    @PostMapping("/register")
    public Driver registerDriver(@RequestBody Driver driver) {

        // ✅ encrypt password before saving
        driver.setPassword(passwordEncoder.encode(driver.getPassword()));

        // default availability
        driver.setAvailable(true);

        return driverRepository.save(driver);
    }

    // URL: http://localhost:8080/drivers
    @GetMapping
    public List<Driver> getAllDrivers() {
        return driverRepository.findAll();
    }
}