package com.example.AirbnbBookingSpring.repositories.writes;

import com.example.AirbnbBookingSpring.models.Availability;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

@Repository
public interface AvailabilityWriteRepository extends JpaRepository<Availability, UUID> {

    List<Availability> findByBookingId(UUID bookingId);

    List<Availability> findByAirbnbId(UUID airbnbId);

    //SELECT * from availability WHERE airbnb_id = airbnbId AND date BETWEEN checkInDate AND checkoutDate
    List<Availability> findByAirbnbIdAndDateBetween(UUID airbnbId, LocalDate checkInDate, LocalDate checkOutDate);

    //SELECT COUNT(*) from availability WHERE airbnb_id = airbnbId AND date BETWEEN checkInDate AND checkoutDate AND booking_is IS NOT NULL
    Long countByAirbnbIdAndDateBetweenAndBookingIdIsNotNull(UUID airbnbId, LocalDate checkInDate, LocalDate checkOutDate);

    @Modifying(clearAutomatically = true)
    @Query("UPDATE Availability a SET a.bookingId = :bookingId WHERE a.airbnbId = :airbnbId AND a.date between :checkInDate AND :checkOutDate")
    void updateBookingIdByAirbnbIdAndDateBetween(UUID bookingId, UUID airbnbId, LocalDate checkInDate, LocalDate checkOutDate);
}
