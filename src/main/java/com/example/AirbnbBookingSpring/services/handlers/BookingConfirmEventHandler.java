package com.example.AirbnbBookingSpring.services.handlers;

import com.example.AirbnbBookingSpring.repositories.writes.AvailabilityWriteRepository;
import com.example.AirbnbBookingSpring.saga.SagaEvent;
import com.example.AirbnbBookingSpring.saga.SagaEventHandler;
import com.example.AirbnbBookingSpring.saga.SagaEventPublisher;
import com.example.AirbnbBookingSpring.saga.SagaEventType;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.Map;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@Slf4j
public class BookingConfirmEventHandler implements SagaEventHandler {

    private final AvailabilityWriteRepository availabilityWriteRepository;

    private final SagaEventPublisher sagaEventPublisher;

    @Override
    public SagaEventType eventType() {
        return SagaEventType.BOOKING_CONFIRMED;
    }

    @Override
    @Transactional
    public void handleEvent(SagaEvent sagaEvent) {
        try {
            Map<String, Object> payload = sagaEvent.getPayload();

            UUID bookingId = UUID.fromString((String) payload.get("bookingId"));
            UUID airbnbId = UUID.fromString((String) payload.get("airbnbId"));

            LocalDate checkInDate = LocalDate.parse((String) payload.get("checkInDate"));
            LocalDate checkOutDate = LocalDate.parse((String) payload.get("checkOutDate"));

            Long count = availabilityWriteRepository.countByAirbnbIdAndDateBetweenAndBookingIdIsNotNull(airbnbId,
                    checkInDate, checkOutDate);

            if (count > 0) {
                sagaEventPublisher.publishEvent(SagaEventType.BOOKING_CANCEL_REQUESTED, "CANCEL_BOOKING", payload);
                throw new RuntimeException(
                        "Airbnb is not available for the given dates. Please try again with different dates.");
            }

            availabilityWriteRepository.updateBookingIdByAirbnbIdAndDateBetween(bookingId, airbnbId, checkInDate,
                    checkOutDate);

        } catch (Exception e) {
            Map<String, Object> payload = sagaEvent.getPayload();
            sagaEventPublisher.publishEvent(SagaEventType.BOOKING_COMPENSATED, "COMPENSATE_BOOKING", payload);
            throw new RuntimeException("Failed to confirm booking", e);
        }
    }
}
