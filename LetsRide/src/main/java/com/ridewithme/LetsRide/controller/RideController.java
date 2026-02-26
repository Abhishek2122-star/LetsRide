package com.ridewithme.LetsRide.controller;

import com.ridewithme.LetsRide.model.Ride;
import com.ridewithme.LetsRide.repository.RideRepository;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/ride")
public class RideController {

    private final RideRepository rideRepository;

    public RideController(RideRepository rideRepository) {
        this.rideRepository = rideRepository;
    }

    //  Create Ride
    @PostMapping
    public Ride createRide(@RequestBody Ride ride) {
        return rideRepository.save(ride);
    }

    //  Get All Rides
    @GetMapping
    public List<Ride> getAllRides() {
        return rideRepository.findAll();
    }
}