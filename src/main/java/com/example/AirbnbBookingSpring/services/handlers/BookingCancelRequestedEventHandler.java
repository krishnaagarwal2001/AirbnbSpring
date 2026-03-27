package com.example.AirbnbBookingSpring.services.handlers;

import com.example.AirbnbBookingSpring.models.Booking;
import com.example.AirbnbBookingSpring.repositories.reads.RedisWriteRepository;
import com.example.AirbnbBookingSpring.repositories.writes.BookingWriteRepository;
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
public class BookingCancelRequestedEventHandler implements SagaEventHandler {

    private final BookingWriteRepository bookingWriteRepository;
    private final RedisWriteRepository redisWriteRepository;

    private final SagaEventPublisher sagaEventPublisher;

    @Override
    public SagaEventType eventType() {
        return SagaEventType.BOOKING_CANCEL_REQUESTED;
    }

    @Override
    @Transactional
    public void handleEvent(SagaEvent sagaEvent) {

        try {
            Map<String, Object> payload = sagaEvent.getPayload();

            UUID bookingId = (UUID) payload.get("bookingId");
            UUID airbnbId = (UUID) payload.get("airbnbId");
            LocalDate checkInDate = (LocalDate) payload.get("checkInDate");
            LocalDate checkOutDate = (LocalDate) payload.get("checkOutDate");

            Booking booking = bookingWriteRepository.findById(bookingId)
                    .orElseThrow(() -> new RuntimeException("Booking not found"));

            booking.setBookingStatus(Booking.BookingStatus.CANCELLED);

            bookingWriteRepository.save(booking);

            redisWriteRepository.writeBookingReadModel(booking);

            // publish BOOKING_CANCELLED eventType
            sagaEventPublisher.publishEvent(SagaEventType.BOOKING_CANCELLED, "CANCEL_BOOKING",
                    Map.of("bookingId", bookingId,
                            "airbnbId", airbnbId,
                            "checkInDate", checkInDate.toString(),
                            "checkOutDate", checkOutDate.toString()));
        } catch (Exception e) {
            Map<String, Object> payload = sagaEvent.getPayload();
            sagaEventPublisher.publishEvent(SagaEventType.BOOKING_COMPENSATED, "COMPENSATE_BOOKING", payload);
            throw new RuntimeException(e);
        }

    }
}
