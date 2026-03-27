package com.example.AirbnbBookingSpring.saga;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Map;

import java.util.UUID;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class SagaEvent implements Serializable {
    private UUID sagaId;

    private SagaEventType eventType;

    private String step;

    private Map<String, Object> payload;

    private LocalDateTime timestamp;

    private SagaStatus status;

    public enum SagaStatus {
        PENDING,
        COMPLETED,
        FAILED,
        COMPENSATED
    }
}
