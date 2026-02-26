package com.ridewithme.LetsRide.repository;

import com.ridewithme.LetsRide.model.Driver;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DriverRepository extends JpaRepository<Driver, Long> {
}