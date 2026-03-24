package com.example.AirbnbBookingSpring.models.readModels;

import com.example.AirbnbBookingSpring.models.BaseModel;
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
public class AvailabilityReadModel extends BaseModel {
    private UUID airbnbId;

    private LocalDate date;

    private UUID bookingId;
}
