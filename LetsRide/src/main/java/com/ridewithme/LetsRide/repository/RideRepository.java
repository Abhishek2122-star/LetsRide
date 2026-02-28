package com.ridewithme.LetsRide.repository;

import com.ridewithme.LetsRide.model.Ride;
import com.ridewithme.LetsRide.model.User;
import com.ridewithme.LetsRide.model.Driver;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface RideRepository extends JpaRepository<Ride, Long> {
    // ✅ Use the actual User object relationship we created
    List<Ride> findByUser(User user);

    // ✅ Added for driver support
    List<Ride> findByDriver(Driver driver);
}