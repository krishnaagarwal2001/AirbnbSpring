package com.example.AirbnbBookingSpring.saga;

public interface IEventPublisher {
    void publish(SagaEvent sagaEvent);
}
