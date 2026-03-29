package com.example.AirbnbBookingSpring.services.airbnb;

import com.example.AirbnbBookingSpring.dtos.CreateAirbnbRequest;
import com.example.AirbnbBookingSpring.dtos.UpdateAirbnbRequest;
import com.example.AirbnbBookingSpring.models.Airbnb;

import java.util.List;
import java.util.UUID;

public interface IAirbnbService {
    Airbnb findAirbnbById(UUID id);

    List<Airbnb> findAllAirbnbs();

    Airbnb createAirbnb(CreateAirbnbRequest createAirbnbRequest);

    Airbnb updateAirbnb(UpdateAirbnbRequest updateAirbnbRequest);
}
