package com.example.AirbnbBookingSpring.repositories.writes;

import com.example.AirbnbBookingSpring.models.Airbnb;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface AirbnbWriteRepository extends JpaRepository<Airbnb, UUID> {
    Optional<Airbnb> findById(UUID airbnbId);
}
