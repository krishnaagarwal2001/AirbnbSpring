package com.example.AirbnbBookingSpring.adapters;

import com.example.AirbnbBookingSpring.dtos.CreateAirbnbRequest;
import com.example.AirbnbBookingSpring.dtos.UpdateAirbnbRequest;
import com.example.AirbnbBookingSpring.models.Airbnb;

public class AirbnbAdapter {
    public static Airbnb createAirbnbRequestToAirbnb(CreateAirbnbRequest createAirbnbRequest) {
        return Airbnb.builder()
                .name(createAirbnbRequest.getName())
                .description(createAirbnbRequest.getDescription())
                .location(createAirbnbRequest.getLocation())
                .pricePerNight(createAirbnbRequest.getPricePerNight())
                .build();
    }

    public static Airbnb updateAirbnbRequestToAirbnb(Airbnb airbnb, UpdateAirbnbRequest updateAirbnbRequest) {
        airbnb.setName(updateAirbnbRequest.getName());
        airbnb.setDescription(updateAirbnbRequest.getDescription());
        airbnb.setLocation(updateAirbnbRequest.getLocation());
        airbnb.setPricePerNight(updateAirbnbRequest.getPricePerNight());

        return airbnb;
    }
}
