package com.ridewithme.LetsRide.controller;

import com.ridewithme.LetsRide.model.Driver;
import com.ridewithme.LetsRide.repository.DriverRepository;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/drivers")
public class DriverController {

    private final DriverRepository driverRepository;

    public DriverController(DriverRepository driverRepository) {
        this.driverRepository = driverRepository;
    }

    // Register Driver
    @PostMapping("/register")
    public Driver registerDriver(@RequestBody Driver driver) {
        return driverRepository.save(driver);
    }

    // 🔹 Get All Drivers
    @GetMapping
    public List<Driver> getAllDrivers() {
        return driverRepository.findAll();
    }
}