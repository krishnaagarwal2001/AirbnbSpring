package com.example.AirbnbBookingSpring.services.airbnb;

import com.example.AirbnbBookingSpring.adapters.AirbnbAdapter;
import com.example.AirbnbBookingSpring.dtos.CreateAirbnbRequest;
import com.example.AirbnbBookingSpring.dtos.UpdateAirbnbRequest;
import com.example.AirbnbBookingSpring.models.Airbnb;
import com.example.AirbnbBookingSpring.repositories.writes.AirbnbWriteRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class AirbnbService implements IAirbnbService {

    private final AirbnbWriteRepository airbnbWriteRepository;

    @Override
    public Airbnb findAirbnbById(UUID id) {
        return airbnbWriteRepository.findById(id)
                .orElseThrow(()-> new RuntimeException("Airbnb does not exist with this id"));
    }

    @Override
    public List<Airbnb> findAllAirbnbs() {
        return airbnbWriteRepository.findAll();
    }

    @Override
    public Airbnb createAirbnb(CreateAirbnbRequest createAirbnbRequest) {
        Airbnb airbnb = AirbnbAdapter.createAirbnbRequestToAirbnb(createAirbnbRequest);
        return airbnbWriteRepository.save(airbnb);
    }

    @Override
    public Airbnb updateAirbnb(UpdateAirbnbRequest updateAirbnbRequest) {
        Airbnb airbnb = airbnbWriteRepository.findById(updateAirbnbRequest.getId())
                .orElseThrow(()-> new RuntimeException("Airbnb does not exist with this id"));

        Airbnb updatedAirbnb = AirbnbAdapter.updateAirbnbRequestToAirbnb(airbnb, updateAirbnbRequest);
        return airbnbWriteRepository.save(updatedAirbnb);
    }
}
