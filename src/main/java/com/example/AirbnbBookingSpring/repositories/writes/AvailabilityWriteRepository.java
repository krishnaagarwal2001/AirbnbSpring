package com.example.AirbnbBookingSpring.repositories.writes;

import com.example.AirbnbBookingSpring.models.Availability;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface AvailabilityWriteRepository extends JpaRepository<Availability, UUID> {

    List<Availability> findByBookingId(UUID bookingId);

    List<Availability> findByAirbnbId(UUID airbnbId);
}
