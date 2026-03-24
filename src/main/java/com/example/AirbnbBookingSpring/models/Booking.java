package com.example.AirbnbBookingSpring.models;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.SuperBuilder;

import java.util.UUID;

@Entity
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
@Data
@EqualsAndHashCode(callSuper = true)
public class Booking extends BaseModel{
    @Column(nullable = false)
    private UUID userId;

    @Column(nullable = false)
    private UUID airbnbId;

    @Column(nullable = false)
    private Double totalPrice;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    @Builder.Default
    private BookingStatus bookingStatus = BookingStatus.PENDING;

    @Column(unique = true)
    private String idempotencyKey;

    public enum BookingStatus {
        PENDING,
        CONFIRMED,
        CANCELLED
    }
}
