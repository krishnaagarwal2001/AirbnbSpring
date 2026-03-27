package com.example.AirbnbBookingSpring.services.handlers;

import com.example.AirbnbBookingSpring.repositories.writes.AvailabilityWriteRepository;
import com.example.AirbnbBookingSpring.saga.SagaEvent;
import com.example.AirbnbBookingSpring.saga.SagaEventHandler;
import com.example.AirbnbBookingSpring.saga.SagaEventPublisher;
import com.example.AirbnbBookingSpring.saga.SagaEventType;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.Map;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class BookingCancelEventHandler implements SagaEventHandler {

    private final AvailabilityWriteRepository availabilityWriteRepository;

    private final SagaEventPublisher sagaEventPublisher;

    @Override
    public SagaEventType eventType() {
        return SagaEventType.BOOKING_CANCELLED;
    }

    @Override
    @Transactional
    public void handleEvent(SagaEvent sagaEvent) {
        try {
            Map<String, Object> payload = sagaEvent.getPayload();

            UUID airbnbId = (UUID) payload.get("airbnbId");

            LocalDate checkInDate = (LocalDate) payload.get("checkInDate");
            LocalDate checkOutDate = (LocalDate) payload.get("checkOutDate");

            availabilityWriteRepository.updateBookingIdByAirbnbIdAndDateBetween(null, airbnbId, checkInDate,
                    checkOutDate);

        } catch (Exception e) {
            Map<String, Object> payload = sagaEvent.getPayload();
            sagaEventPublisher.publishEvent(SagaEventType.BOOKING_COMPENSATED, "COMPENSATE_BOOKING", payload);
            throw new RuntimeException("Failed to confirm booking", e);
        }
    }
}
