package com.example.AirbnbBookingSpring.saga;


public interface SagaEventHandler {
    SagaEventType eventType();

    void handleEvent(SagaEvent sagaEvent);
}