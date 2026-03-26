package com.example.AirbnbBookingSpring.services;

import com.example.AirbnbBookingSpring.models.Booking;

import java.util.Optional;
import java.util.UUID;

public interface IIdempotencyService {
    boolean isIdempotencyKeyUsed(UUID idempotencyKey);

    Optional<Booking> findBookingByIdempotencyKey(UUID idempotencyKey);
}
