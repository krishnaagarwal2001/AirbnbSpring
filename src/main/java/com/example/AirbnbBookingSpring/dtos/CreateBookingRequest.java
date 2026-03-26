package com.example.AirbnbBookingSpring.dtos;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.time.LocalDate;
import java.util.UUID;

@Data
public class CreateBookingRequest {

    @NotNull(message = "Airbnb Id is required")
    private UUID airbnbId;

    @NotNull(message = "User Id is required")
    private UUID userId;

    @NotNull(message = "Check in Date is required")
    private LocalDate checkInDate;

    @NotNull(message = "Check out Date is required")
    private LocalDate checkOutDate;
}
