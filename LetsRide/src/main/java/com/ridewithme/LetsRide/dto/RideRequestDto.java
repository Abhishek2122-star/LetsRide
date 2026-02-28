package com.ridewithme.LetsRide.dto;

import lombok.Data;

@Data
public class RideRequestDto {
    private Long userId;
    private String pickupLocation;
    private String dropLocation;
}