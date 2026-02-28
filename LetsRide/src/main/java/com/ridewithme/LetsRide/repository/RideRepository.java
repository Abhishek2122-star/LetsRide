package com.ridewithme.LetsRide.repository;

import com.ridewithme.LetsRide.model.Ride;
import com.ridewithme.LetsRide.model.User;
import com.ridewithme.LetsRide.model.Driver;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface RideRepository extends JpaRepository<Ride, Long> {

    // Find all rides by a user
    List<Ride> findByUser(User user);

    // Find all rides by a driver
    List<Ride> findByDriver(Driver driver);
}