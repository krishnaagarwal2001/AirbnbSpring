package com.example.AirbnbBookingSpring.adapters;

import com.example.AirbnbBookingSpring.dtos.CreateBookingRequest;
import com.example.AirbnbBookingSpring.models.Booking;
import com.example.AirbnbBookingSpring.models.readModels.BookingReadModel;

import java.util.UUID;

public class BookingAdapter {
    public static Booking createBookingRequestToBooking(CreateBookingRequest createBookingRequest, Double totalPrice){
        return Booking.builder()
                .airbnbId(createBookingRequest.getAirbnbId())
                .userId(createBookingRequest.getUserId())
                .totalPrice(totalPrice)
                .bookingStatus(Booking.BookingStatus.PENDING)
                .idempotencyKey(UUID.randomUUID())
                .checkInDate(createBookingRequest.getCheckInDate())
                .checkOutDate(createBookingRequest.getCheckOutDate())
                .build();
    }

    public static BookingReadModel bookingToBookingReadModel(Booking booking){
        return BookingReadModel.builder()
                .airbnbId(booking.getAirbnbId())
                .userId(booking.getUserId())
                .totalPrice(booking.getTotalPrice())
                .bookingStatus(booking.getBookingStatus())
                .idempotencyKey(booking.getIdempotencyKey())
                .checkInDate(booking.getCheckInDate())
                .checkOutDate(booking.getCheckOutDate())
                .build();
    }

    public static Booking bookingReadModelToBooking(BookingReadModel bookingReadModel){
        return Booking.builder()
                .userId(bookingReadModel.getUserId())
                .airbnbId(bookingReadModel.getAirbnbId())
                .totalPrice(bookingReadModel.getTotalPrice())
                .bookingStatus(bookingReadModel.getBookingStatus())
                .idempotencyKey(bookingReadModel.getIdempotencyKey())
                .checkInDate(bookingReadModel.getCheckInDate())
                .checkOutDate(bookingReadModel.getCheckOutDate())
                .build();
    }
}
