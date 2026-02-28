package com.ridewithme.LetsRide.model;

import com.fasterxml.jackson.annotation.JsonIgnore; // 👈 Import this
import jakarta.persistence.*;
import lombok.Data;
import java.time.LocalDateTime;
import org.hibernate.annotations.CreationTimestamp; // 👈 Useful for automatic dates

@Entity
@Data
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    @Column(unique = true)
    private String email;

    @JsonIgnore // 👈 CRITICAL: This hides the password from API responses
    private String password;

    private String role = "USER";

    @CreationTimestamp // 👈 Automatically sets the date when a user is created
    private LocalDateTime createdAt;
}