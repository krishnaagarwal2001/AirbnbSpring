package com.example.AirbnbBookingSpring.services.concurrency;


import com.example.AirbnbBookingSpring.models.Availability;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

public interface ConcurrencyControlStrategy {
    void releaseLock(UUID airbnbId, LocalDate checkInDate, LocalDate checkOutDate);

    List<Availability> lockAndCheckAvailability(UUID airbnbId,UUID userId, LocalDate checkInDate, LocalDate checkOutDate);
}
