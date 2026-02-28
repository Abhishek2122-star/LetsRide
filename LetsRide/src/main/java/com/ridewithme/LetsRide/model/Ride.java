//package com.ridewithme.LetsRide.model;
//
//import com.ridewithme.LetsRide.model.enums.RideStatus;
//import jakarta.persistence.*;
//import lombok.*;
//
//import java.time.LocalDateTime;
//
//@Entity
//@Table(name = "rides")
//@Data
//@NoArgsConstructor
//@AllArgsConstructor
//@Builder
//public class Ride {
//
//    @Id
//    @GeneratedValue(strategy = GenerationType.IDENTITY)
//    private Long id;
//
//    private String pickupLocation;
//
//    private String dropLocation;
//
//    // ✅ ENUM instead of String
//    @Enumerated(EnumType.STRING)
//    private RideStatus status;
//
//    // ⏱ Ride lifecycle timestamps
//    private LocalDateTime requestedAt;
//
//    private LocalDateTime startedAt;
//
//    private LocalDateTime completedAt;
//
//    // 👤 Many rides can be booked by one user
//    @ManyToOne
//    @JoinColumn(name = "user_id")
//    private User user;
//
//    // 🚗 Many rides can be handled by one driver
//    @ManyToOne
//    @JoinColumn(name = "driver_id")
//    private Driver driver;
//
//    // 🔥 Automatically set defaults before insert
//    @PrePersist
//    public void prePersist() {
//        if (status == null) {
//            status = RideStatus.REQUESTED;
//        }
//
//        if (requestedAt == null) {
//            requestedAt = LocalDateTime.now();
//        }
//    }
//}

package com.ridewithme.LetsRide.model;

import com.ridewithme.LetsRide.model.enums.RideStatus;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "rides")
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

    // ✅ ENUM instead of String
    @Enumerated(EnumType.STRING)
    private RideStatus status;

    // ⏱ Ride lifecycle timestamps
    private LocalDateTime requestedAt;

    private LocalDateTime startedAt;

    private LocalDateTime completedAt;

    // 👤 Many rides can be booked by one user
    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    // 🚗 Many rides can be handled by one driver
    @ManyToOne
    @JoinColumn(name = "driver_id")
    private Driver driver;

    // 🔥 Automatically set defaults before insert
    @PrePersist
    public void prePersist() {
        if (status == null) {
            status = RideStatus.REQUESTED;
        }

        if (requestedAt == null) {
            requestedAt = LocalDateTime.now();
        }
    }
}