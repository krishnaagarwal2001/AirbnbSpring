package com.example.AirbnbBookingSpring.models.readModels;


import com.example.AirbnbBookingSpring.models.BaseModel;
import com.example.AirbnbBookingSpring.models.Booking;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import java.time.LocalDate;
import java.util.UUID;

@Data
@EqualsAndHashCode(callSuper = true)
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
public class BookingReadModel extends BaseModel {
    private UUID airbnbId;

    private UUID userId;

    private double totalPrice;

    private Booking.BookingStatus bookingStatus;

    private UUID idempotencyKey;

    private LocalDate checkInDate;

    private LocalDate checkOutDate;

}
