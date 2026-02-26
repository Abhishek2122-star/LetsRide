package com.ridewithme.LetsRide.model;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "Rides")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Ride {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String pickupLocation;

    private String dropLocation;

    private String status;
    // Example: REQUESTED, ACCEPTED, COMPLETED

    // 🔹 Many rides can belong to one user
    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    // 🔹 Many rides can be assigned to one driver
    @ManyToOne
    @JoinColumn(name = "driver_id")
    private Driver driver;
}