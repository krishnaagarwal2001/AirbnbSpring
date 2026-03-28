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
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.Map;
import java.util.UUID;

//updates booking status to confirmed
@Service
@RequiredArgsConstructor
@Slf4j
public class BookingConfirmRequestedEventHandler implements SagaEventHandler {

    private final BookingWriteRepository bookingWriteRepository;
    private final RedisWriteRepository redisWriteRepository;

    private final SagaEventPublisher sagaEventPublisher;

    @Override
    public SagaEventType eventType() {
        return SagaEventType.BOOKING_CONFIRM_REQUESTED;
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

            Booking booking = bookingWriteRepository.findById(bookingId)
                    .orElseThrow(() -> new RuntimeException("Booking not found"));

            booking.setBookingStatus(Booking.BookingStatus.CONFIRMED);

            // save in db
            bookingWriteRepository.save(booking);

            // save in redis
            redisWriteRepository.writeBookingReadModel(booking);

            // publish BOOKING_CONFIRMED eventType
            sagaEventPublisher.publishEvent(SagaEventType.BOOKING_CONFIRMED, "CONFIRM_BOOKING",
                    Map.of("bookingId", bookingId,
                            "airbnbId", airbnbId,
                            "checkInDate", checkInDate.toString(),
                            "checkOutDate", checkOutDate.toString()));
        } catch (Exception e) {
            Map<String, Object> payload = sagaEvent.getPayload();
            // publish BOOKING_COMPENSATED eventType
            sagaEventPublisher.publishEvent(SagaEventType.BOOKING_COMPENSATED, "COMPENSATE_BOOKING", payload);
            throw new RuntimeException("Failed to confirm booking", e);
        }

    }
}
