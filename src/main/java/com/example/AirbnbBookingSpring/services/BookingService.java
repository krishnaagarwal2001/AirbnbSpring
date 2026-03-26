package com.example.AirbnbBookingSpring.services;

import com.example.AirbnbBookingSpring.adapters.BookingAdapter;
import com.example.AirbnbBookingSpring.dtos.CreateBookingRequest;
import com.example.AirbnbBookingSpring.dtos.UpdateBookingRequest;
import com.example.AirbnbBookingSpring.models.Airbnb;
import com.example.AirbnbBookingSpring.models.Booking;
import com.example.AirbnbBookingSpring.models.User;
import com.example.AirbnbBookingSpring.repositories.reads.RedisWriteRepository;
import com.example.AirbnbBookingSpring.repositories.writes.AirbnbWriteRepository;
import com.example.AirbnbBookingSpring.repositories.writes.AvailabilityWriteRepository;
import com.example.AirbnbBookingSpring.repositories.writes.BookingWriteRepository;
import com.example.AirbnbBookingSpring.repositories.writes.UserWriteRepository;
import com.example.AirbnbBookingSpring.services.concurrency.ConcurrencyControlStrategy;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;

@Service
@RequiredArgsConstructor
public class BookingService implements  IBookingService {
    private final BookingWriteRepository bookingWriteRepository;
    private final AvailabilityWriteRepository availabilityWriteRepository;
    private final AirbnbWriteRepository airbnbWriteRepository;
    private final UserWriteRepository userWriteRepository;

    private final ConcurrencyControlStrategy concurrencyControlStrategy;

    private final RedisWriteRepository redisWriteRepository;

    private final IIdempotencyService idempotencyService;

    @Override
    @Transactional
    public Booking createBooking(CreateBookingRequest createBookingRequest) {
       Airbnb airbnb = airbnbWriteRepository.findById(createBookingRequest.getAirbnbId()).orElseThrow(()-> new RuntimeException("Airbnb not found"));

       User user = userWriteRepository.findById(createBookingRequest.getUserId()).orElseThrow(()-> new RuntimeException("User not found"));

       if(createBookingRequest.getCheckInDate().isAfter(createBookingRequest.getCheckOutDate())) {
           throw new RuntimeException("Check in date should be before check out date");
       }

        if (createBookingRequest.getCheckOutDate().isBefore(LocalDate.now())) {
          throw new RuntimeException("Check-in date must be today or in the future");
        }

        concurrencyControlStrategy.lockAndCheckAvailability(
                createBookingRequest.getAirbnbId(),
                createBookingRequest.getUserId(),
                createBookingRequest.getCheckInDate(),
                createBookingRequest.getCheckOutDate()
                );

        Long numberOfNights = ChronoUnit.DAYS.between(createBookingRequest.getCheckOutDate(), LocalDate.now());

        Double totalPrice = airbnb.getPricePerNight() * numberOfNights;

        Booking newBooking = BookingAdapter.createBookingRequestToBooking(createBookingRequest,totalPrice);

        newBooking = bookingWriteRepository.save(newBooking);

        redisWriteRepository.writeBookingReadModel(newBooking);

        return newBooking;
    }

    @Override
    public Booking updateBooking(UpdateBookingRequest updateBookingRequest) {
        Booking booking = idempotencyService.findBookingByIdempotencyKey(updateBookingRequest.getIdempotencyKey())
                .orElseThrow(()-> new RuntimeException("Idempotency Key not found"));

        if(booking.getBookingStatus() != Booking.BookingStatus.PENDING) {
            throw new RuntimeException("Booking is not pending");
        }

        return booking;
    }
}
