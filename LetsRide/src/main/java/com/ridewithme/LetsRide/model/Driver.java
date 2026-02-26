package com.ridewithme.LetsRide.model;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "Drivers")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Driver {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    @Column(unique = true)
    private String email;

    private String phone;

    private String vehicleNumber;

    private String licenseNumber;

    private boolean available = true;
}