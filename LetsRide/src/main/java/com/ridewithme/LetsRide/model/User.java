package com.ridewithme.LetsRide.model;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import org.hibernate.annotations.CreationTimestamp; // 👈 Add this
import java.time.LocalDateTime; // 👈 Add this

@Entity
@Table(name = "users")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;
    private String email;
    private String password;
    private String role = "USER";

    @CreationTimestamp // 👈 This automatically sets the date when saved
    private LocalDateTime createdAt; // 👈 This is the "symbol" the error is looking for
}