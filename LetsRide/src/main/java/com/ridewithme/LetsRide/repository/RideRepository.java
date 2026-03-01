package com.ridewithme.LetsRide.repository;

import com.ridewithme.LetsRide.model.Ride;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface RideRepository extends JpaRepository<Ride, Long> {
    // We use userId because that is the field name in your Ride.java
    List<Ride> findByUserId(Long userId);
}