package com.ridewithme.LetsRide.controller;

import com.ridewithme.LetsRide.model.Driver;
import com.ridewithme.LetsRide.repository.DriverRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/drivers")
@RequiredArgsConstructor // Automatically creates the constructor for driverRepository
public class DriverController {

    private final DriverRepository driverRepository;

    // ✅ URL: http://localhost:8080/drivers/register
    @PostMapping("/register")
    public Driver registerDriver(@RequestBody Driver driver) {
        // Set default availability
        driver.setAvailable(true);
        return driverRepository.save(driver);
    }

    // 🔹 URL: http://localhost:8080/drivers
    @GetMapping
    public List<Driver> getAllDrivers() {
        return driverRepository.findAll();
    }
}