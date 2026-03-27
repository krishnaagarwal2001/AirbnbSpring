package com.example.AirbnbBookingSpring.services.handlers;

import com.example.AirbnbBookingSpring.saga.SagaEvent;
import com.example.AirbnbBookingSpring.saga.SagaEventHandler;
import com.example.AirbnbBookingSpring.saga.SagaEventType;

public class BookingCompensatedEventHandler implements SagaEventHandler {
    @Override
    public SagaEventType eventType() {
        return SagaEventType.BOOKING_COMPENSATED;
    }

    @Override
    public void handleEvent(SagaEvent sagaEvent) {

    }
}
