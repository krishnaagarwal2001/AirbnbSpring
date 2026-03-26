package com.example.AirbnbBookingSpring.dtos;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.time.LocalDate;
import java.util.UUID;

@Data
public class CreateBookingRequest {

    @NotNull(message = "Airbnb Id is required")
    private UUID airbnbId;

    @NotNull(message = "user Id is required")
    private UUID userId;

    @NotNull(message = "start Date is required")
    private LocalDate startDate;

    @NotNull(message = "end Date is required")
    private LocalDate endDate;
}
