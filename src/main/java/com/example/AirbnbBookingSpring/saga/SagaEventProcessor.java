package com.example.AirbnbBookingSpring.saga;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class SagaEventProcessor{
    private final SagaEventHandlerRegistry sagaEventHandlerRegistry;

    public void processEvent(SagaEvent sagaEvent) {
        sagaEventHandlerRegistry.getSagaEventHandler(sagaEvent.getEventType())
                .ifPresent(sagaEventHandler-> sagaEventHandler.handleEvent(sagaEvent));
    }
}