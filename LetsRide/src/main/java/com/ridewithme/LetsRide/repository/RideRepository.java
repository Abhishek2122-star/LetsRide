package com.ridewithme.LetsRide.repository;

import com.ridewithme.LetsRide.model.Ride;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RideRepository extends JpaRepository<Ride, Long> {
}