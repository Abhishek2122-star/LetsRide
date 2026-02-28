package com.ridewithme.LetsRide.repository;

import com.ridewithme.LetsRide.model.RideRequest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RideRequestRepository extends JpaRepository<RideRequest, Long> {

    // Find all rides by a specific user
    List<RideRequest> findByUserId(Long userId);
}