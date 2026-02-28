package com.ridewithme.LetsRide.model;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Table(name = "ride_requests")
public class RideRequest {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // Many ride requests can belong to one user
    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    private String pickupLocation;
    private String dropLocation;

    private String status = "PENDING"; // default status
}