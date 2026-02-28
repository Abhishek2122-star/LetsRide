package com.ridewithme.LetsRide.service;

import com.ridewithme.LetsRide.model.Driver;
import com.ridewithme.LetsRide.model.Ride;
import com.ridewithme.LetsRide.model.User;
import com.ridewithme.LetsRide.model.enums.RideStatus;
import com.ridewithme.LetsRide.repository.DriverRepository;
import com.ridewithme.LetsRide.repository.RideRepository;
import com.ridewithme.LetsRide.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor // 👈 Simplifies the constructor for you
public class RideService {

    private final RideRepository rideRepository;
    private final UserRepository userRepository;
    private final DriverRepository driverRepository;

    // 1️⃣ Book a new ride
    public Ride requestRide(Long userId, String pickup, String drop) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));

        // ✅ Now .user() and .dropLocation() will work perfectly
        Ride ride = Ride.builder()
                .user(user)
                .pickupLocation(pickup)
                .dropLocation(drop) // Matches the field in Ride.java
                .status(RideStatus.REQUESTED)
                .requestedAt(LocalDateTime.now())
                .build();

        return rideRepository.save(ride);
    }

    // 2️⃣ Driver accepts ride
    public Ride acceptRide(Long rideId, Long driverId) {
        Ride ride = rideRepository.findById(rideId)
                .orElseThrow(() -> new RuntimeException("Ride not found"));

        if (ride.getStatus() != RideStatus.REQUESTED) {
            throw new RuntimeException("Ride cannot be accepted (Status is: " + ride.getStatus() + ")");
        }

        Driver driver = driverRepository.findById(driverId)
                .orElseThrow(() -> new RuntimeException("Driver not found"));

        ride.setDriver(driver);
        ride.setStatus(RideStatus.ACCEPTED);

        return rideRepository.save(ride);
    }

    // 3️⃣ Start ride
    public Ride startRide(Long rideId) {
        Ride ride = rideRepository.findById(rideId)
                .orElseThrow(() -> new RuntimeException("Ride not found"));

        if (ride.getStatus() != RideStatus.ACCEPTED) {
            throw new RuntimeException("Ride cannot be started");
        }

        ride.setStatus(RideStatus.ONGOING);
        ride.setStartedAt(LocalDateTime.now());

        return rideRepository.save(ride);
    }

    // 4️⃣ Complete ride
    public Ride completeRide(Long rideId) {
        Ride ride = rideRepository.findById(rideId)
                .orElseThrow(() -> new RuntimeException("Ride not found"));

        if (ride.getStatus() != RideStatus.ONGOING) {
            throw new RuntimeException("Ride cannot be completed");
        }

        ride.setStatus(RideStatus.COMPLETED);
        ride.setCompletedAt(LocalDateTime.now());

        return rideRepository.save(ride);
    }

    // 5️⃣ Cancel ride
    public Ride cancelRide(Long rideId) {
        Ride ride = rideRepository.findById(rideId)
                .orElseThrow(() -> new RuntimeException("Ride not found"));

        ride.setStatus(RideStatus.CANCELLED);
        ride.setCompletedAt(LocalDateTime.now());

        return rideRepository.save(ride);
    }

    // 6️⃣ Get all rides
    public List<Ride> getAllRides() {
        return rideRepository.findAll();
    }

    // 7️⃣ Get rides by user
    public List<Ride> getRidesByUser(Long userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));

        return rideRepository.findByUser(user);
    }

    // 8️⃣ Get rides by driver
    public List<Ride> getRidesByDriver(Long driverId) {
        Driver driver = driverRepository.findById(driverId)
                .orElseThrow(() -> new RuntimeException("Driver not found"));

        return rideRepository.findByDriver(driver);
    }
}