package com.example.AirbnbBookingSpring.models.readModels;

import com.example.AirbnbBookingSpring.models.BaseModel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import java.util.List;

@Data
@EqualsAndHashCode(callSuper = true)
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
public class AirbnbReadModel extends BaseModel {
    private String name;

    private String description;

    private String location;

    private Double pricePerNight;

    private List<AvailabilityReadModel> availability;
}
