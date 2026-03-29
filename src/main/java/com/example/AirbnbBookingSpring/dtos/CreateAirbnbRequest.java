package com.example.AirbnbBookingSpring.dtos;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.Data;

@Data
public class CreateAirbnbRequest {
    @NotNull(message = "Name is required")
    private String name;

    @NotNull(message = "Description is required")
    private String description;

    @NotNull(message = "Location is required")
    private String location;

    @NotNull(message = "Location is required")
    @Positive(message = "Price must be greater than 0")
    private Double pricePerNight;
}
