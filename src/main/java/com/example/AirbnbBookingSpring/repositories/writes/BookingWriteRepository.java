package com.example.AirbnbBookingSpring.repositories.writes;

import com.example.AirbnbBookingSpring.models.Booking;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface BookingWriteRepository extends JpaRepository<Booking, UUID> {

    Optional<Booking> findById(UUID airbnbId);

    List<Booking> findByAirbnbId(UUID airbnbId);

    Optional<Booking> findByIdempotencyKey(String idempotencyKey);

//     @Lock(LockModeType.PESSIMISTIC_WRITE)
//     @Query("SELECT b FROM Booking b WHERE b.id = :id")
//     Optional<Booking> findByIdWithLock( @Param("id") UUID id);
}
