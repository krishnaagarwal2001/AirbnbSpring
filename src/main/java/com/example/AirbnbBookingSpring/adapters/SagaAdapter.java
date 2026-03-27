package com.example.AirbnbBookingSpring.adapters;

import com.example.AirbnbBookingSpring.saga.SagaEvent;
import com.example.AirbnbBookingSpring.saga.SagaEventType;

import java.time.LocalDateTime;
import java.util.Map;
import java.util.UUID;

public class SagaAdapter {
    public static SagaEvent createSagaEvent(SagaEventType eventType, String step, Map<String, Object> payload){
        return SagaEvent.builder()
                .sagaId(UUID.randomUUID())
                .eventType(eventType)
                .step(step)
                .payload(payload)
                .timestamp(LocalDateTime.now())
                .status(SagaEvent.SagaStatus.PENDING)
                .build();

    }
}
