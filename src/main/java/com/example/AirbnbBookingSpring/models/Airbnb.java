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
public class Airbnb extends BaseModel{
    private String name;

    private String description;

    @Column(nullable = false)
    private Double pricePerNight;

    private String location;
}
