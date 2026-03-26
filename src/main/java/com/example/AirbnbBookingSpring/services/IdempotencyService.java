package com.example.AirbnbBookingSpring.services;

import com.example.AirbnbBookingSpring.adapters.BookingAdapter;
import com.example.AirbnbBookingSpring.models.Booking;
import com.example.AirbnbBookingSpring.models.readModels.BookingReadModel;
import com.example.AirbnbBookingSpring.repositories.reads.RedisReadRepository;
import com.example.AirbnbBookingSpring.repositories.writes.BookingWriteRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class IdempotencyService implements IIdempotencyService{

    private final RedisReadRepository redisReadRepository;
    private final BookingWriteRepository bookingWriteRepository;

    @Override
    public boolean isIdempotencyKeyUsed(UUID idempotencyKey) {
        return this.findBookingByIdempotencyKey(idempotencyKey).isPresent();
    }

    @Override
    public Optional<Booking> findBookingByIdempotencyKey(UUID idempotencyKey) {
        BookingReadModel bookingReadModel = redisReadRepository.findBookingByIdempotencyKey(idempotencyKey);

        if(bookingReadModel != null) {
            return Optional.of(BookingAdapter.bookingReadModelToBooking(bookingReadModel));
        }

        //also checks in db, if not find in redis
        return bookingWriteRepository.findByIdempotencyKey(idempotencyKey);
    }
}
