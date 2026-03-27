package com.example.AirbnbBookingSpring.saga;

import com.example.AirbnbBookingSpring.adapters.SagaAdapter;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Map;

@Component
@RequiredArgsConstructor
public class SagaEventPublisher {
    private final IEventPublisher eventPublisher;

    public void publishEvent(SagaEventType eventType, String step, Map<String, Object> payload) {
        SagaEvent sagaEvent = SagaAdapter.createSagaEvent(eventType, step, payload);
        eventPublisher.publish(sagaEvent);
    }
}
