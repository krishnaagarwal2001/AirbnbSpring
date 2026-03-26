package com.example.AirbnbBookingSpring.dtos;

import com.example.AirbnbBookingSpring.models.Booking;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.util.UUID;

@Data
public class UpdateBookingRequest {
    @NotNull(message = "Booking Id is required")
    private UUID bookingId;

    @NotNull(message = "Idempotency key is required")
    private UUID idempotencyKey;

    @NotNull(message = "Booking status is required")
    private Booking.BookingStatus bookingStatus;
}
