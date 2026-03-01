package com.ridewithme.LetsRide.model;

import com.ridewithme.LetsRide.model.enums.RideStatus;
import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "rides")
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Ride {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String pickupLocation;
    private String dropLocation;
    private String rideType;

    @Enumerated(EnumType.STRING)
    private RideStatus status;

    private LocalDateTime requestedAt;
    private LocalDateTime startedAt;
    private LocalDateTime completedAt;

    // Use IDs instead of full objects to keep it simple and match your DB
    private Long userId;
    private Long driverId;
}