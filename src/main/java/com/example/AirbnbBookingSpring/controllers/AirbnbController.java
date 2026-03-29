package com.example.AirbnbBookingSpring.controllers;

import com.example.AirbnbBookingSpring.dtos.CreateAirbnbRequest;
import com.example.AirbnbBookingSpring.dtos.UpdateAirbnbRequest;
import com.example.AirbnbBookingSpring.models.Airbnb;
import com.example.AirbnbBookingSpring.services.airbnb.AirbnbService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/airbnbs")
public class AirbnbController {
    private final AirbnbService airbnbService;

    @PostMapping
    public ResponseEntity<Airbnb> createAirbnb(@RequestBody CreateAirbnbRequest createAirbnbRequest){
        return ResponseEntity.ok(airbnbService.createAirbnb(createAirbnbRequest));
    }

    @PutMapping
    public ResponseEntity<Airbnb> updateAirbnb(@RequestBody UpdateAirbnbRequest updateAirbnbRequest){
        return ResponseEntity.ok(airbnbService.updateAirbnb(updateAirbnbRequest));
    }

    @GetMapping("/{airbnbId}")
    public ResponseEntity<Airbnb> findAirbnbId(@PathVariable String airbnbId){
        return ResponseEntity.ok(airbnbService.findAirbnbById(UUID.fromString(airbnbId)));
    }

    @GetMapping("all")
    public ResponseEntity<List<Airbnb>> findAllAirbnb(){
        return ResponseEntity.ok(airbnbService.findAllAirbnbs());
    }

}
