package com.example.AirbnbBookingSpring.saga;

import com.example.AirbnbBookingSpring.models.Booking;

import java.util.Map;
import java.util.Optional;

public record SagaDispatch(SagaEventType eventType, String step) {
    private static final Map<Booking.BookingStatus, SagaDispatch> DISPATCH_BY_BOOKING_STATUS = Map.of(
            Booking.BookingStatus.CONFIRMED, new SagaDispatch(SagaEventType.BOOKING_CONFIRM_REQUESTED, "CONFIRM_BOOKING"),
            Booking.BookingStatus.CANCELLED, new SagaDispatch(SagaEventType.BOOKING_CANCEL_REQUESTED, "CANCEL_BOOKING")
    );

    public static Optional<SagaDispatch> fromBookingStatus(Booking.BookingStatus bookingStatus) {
        return Optional.ofNullable(DISPATCH_BY_BOOKING_STATUS.get(bookingStatus));
    }
}

