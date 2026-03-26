package com.example.AirbnbBookingSpring.repositories.reads;

import com.example.AirbnbBookingSpring.adapters.BookingAdapter;
import com.example.AirbnbBookingSpring.models.Booking;
import com.example.AirbnbBookingSpring.models.readModels.BookingReadModel;
import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Repository;
import tools.jackson.databind.ObjectMapper;

@Repository
@RequiredArgsConstructor
public class RedisWriteRepository {

    private final ObjectMapper objectMapper;

    private final RedisTemplate<String, String> redisTemplate;

    private void saveBookingReadModel(BookingReadModel bookingReadModel) {
        String key = RedisReadRepository.BOOKING_KEY_PREFIX + bookingReadModel.getId();
        String value = objectMapper.writeValueAsString(bookingReadModel);

        redisTemplate.opsForValue().set(key, value);
    }

    public void writeBookingReadModel(Booking booking) {
        BookingReadModel bookingReadModel = BookingAdapter.bookingToBookingReadModel(booking);

        saveBookingReadModel(bookingReadModel);
    }
}
