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

    public Ride requestRide(Long userId, String pickup, String drop, String type) {
        // We don't necessarily need to fetch the whole User object if we just need the ID,
        // but this check ensures the user actually exists in the DB.
        if (!userRepository.existsById(userId)) {
            throw new RuntimeException("User not found");
        }

        Ride ride = Ride.builder()
                .userId(userId)
                .pickupLocation(pickup)
                .dropLocation(drop)
                .rideType(type)
                .status(RideStatus.REQUESTED)
                .requestedAt(LocalDateTime.now())
                .build();

        return rideRepository.save(ride);
    }

    public List<Ride> getAllRides() {
        return rideRepository.findAll();
    }

    public Ride updateRideStatus(Long rideId, RideStatus status) {
        Ride ride = rideRepository.findById(rideId)
                .orElseThrow(() -> new RuntimeException("Ride not found"));

        ride.setStatus(status);

        // Handle timestamps based on status transitions
        if (status == RideStatus.ONGOING) {
            ride.setStartedAt(LocalDateTime.now());
        } else if (status == RideStatus.COMPLETED) {
            ride.setCompletedAt(LocalDateTime.now());
        }

        return rideRepository.save(ride);
    }

    public List<Ride> getRidesByUser(Long userId) {
        // ✅ Optimization: Use the repository method instead of streaming all records
        return rideRepository.findByUserId(userId);
    }
    public List<Ride> getAvailableRides() {
        return rideRepository.findByStatus(RideStatus.REQUESTED);
    }
    public Ride acceptRide(Long rideId, Long driverId) {

        Ride ride = rideRepository.findById(rideId)
                .orElseThrow(() -> new RuntimeException("Ride not found"));

        if (ride.getStatus() != RideStatus.REQUESTED) {
            throw new RuntimeException("Ride already taken");
        }

        ride.setDriverId(driverId);
        ride.setStatus(RideStatus.ACCEPTED);

        return rideRepository.save(ride);
    }
}