package com.ridewithme.LetsRide.service;

import com.ridewithme.LetsRide.model.Ride;
import com.ridewithme.LetsRide.model.User;
import com.ridewithme.LetsRide.model.enums.RideStatus;
import com.ridewithme.LetsRide.repository.RideRepository;
import com.ridewithme.LetsRide.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
public class RideService {

    private final RideRepository rideRepository;
    private final UserRepository userRepository;

    // 1️⃣ Book a new ride
    public Ride requestRide(Long userId, String pickup, String drop, String type) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));

        Ride ride = Ride.builder()
                .userId(user.getId())
                .pickupLocation(pickup)
                .dropLocation(drop)
                .rideType(type)
                .status(RideStatus.REQUESTED)
                .requestedAt(LocalDateTime.now())
                .build();

        return rideRepository.save(ride);
    }

    // 2️⃣ Get all rides (This is what your React Dashboard uses)
    public List<Ride> getAllRides() {
        return rideRepository.findAll();
    }

    // 3️⃣ Update Ride Status (For completing/cancelling)
    public Ride updateRideStatus(Long rideId, RideStatus status) {
        Ride ride = rideRepository.findById(rideId)
                .orElseThrow(() -> new RuntimeException("Ride not found"));

        ride.setStatus(status);
        if (status == RideStatus.COMPLETED) {
            ride.setCompletedAt(LocalDateTime.now());
        }
        return rideRepository.save(ride);
    }

    // 4️⃣ Get rides by user
    public List<Ride> getRidesByUser(Long userId) {
        return rideRepository.findAll().stream()
                .filter(ride -> ride.getUserId().equals(userId))
                .toList();
    }
}