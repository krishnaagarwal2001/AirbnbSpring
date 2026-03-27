package com.example.AirbnbBookingSpring.models;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.SuperBuilder;

import java.time.LocalDate;
import java.util.UUID;

@Entity
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
@Data
@EqualsAndHashCode(callSuper = true)
public class Availability extends BaseModel {

    @Column(nullable = false)
    private UUID airbnbId;

    private UUID bookingId; // null if available

    @Column(nullable = false)
    private LocalDate date;
}
