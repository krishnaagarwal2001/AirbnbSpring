package com.example.AirbnbBookingSpring.repositories.reads;

import com.example.AirbnbBookingSpring.models.Airbnb;
import com.example.AirbnbBookingSpring.models.readModels.AirbnbReadModel;
import com.example.AirbnbBookingSpring.models.readModels.AvailabilityReadModel;
import com.example.AirbnbBookingSpring.models.readModels.BookingReadModel;
import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Repository;
import tools.jackson.core.JacksonException;
import tools.jackson.databind.ObjectMapper;

import java.util.List;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

@Repository
@RequiredArgsConstructor
public class RedisReadRepository {
    private static final String AIRBNB_KEY_PREFIX = "airbnb:";
    private static final String BOOKING_KEY_PREFIX = "booking:";
    private static final String AVAILABILITY_KEY_PREFIX = "availability:";

    private final RedisTemplate<String,String> redisTemplate;
    private final ObjectMapper objectMapper;


    public AirbnbReadModel findById(UUID airbnbId) {
        String key = AIRBNB_KEY_PREFIX + airbnbId;

        String value = redisTemplate.opsForValue().get(key);

        if(value == null){
            return null;
        }

        try {
            return objectMapper.readValue(value, AirbnbReadModel.class);
        }catch (JacksonException e){
            throw new RuntimeException("Failed to parse Airbnb read model from Redis", e);
        }
    }

    public List<AirbnbReadModel> findAllAirbnbs(){
        Set<String> keys = redisTemplate.keys(BOOKING_KEY_PREFIX + "*");

        if(keys.isEmpty() || keys == null){
            return List.of();
        }

        return keys.stream()
                .map(key -> {
                    String value = redisTemplate.opsForValue().get(key);

                    if(value == null){
                        return null;
                    }
                    try {
                        return objectMapper.readValue(value, AirbnbReadModel.class);
                    }catch (JacksonException e){
                        throw new RuntimeException("Failed to parse Airbnb read model from Redis", e);
                    }
                })
                .filter(airbnb -> airbnb!=null)
                .collect(Collectors.toList());

    }

    public BookingReadModel findBookingById(UUID bookingId) {
        String key = BOOKING_KEY_PREFIX + bookingId;

        String value = redisTemplate.opsForValue().get(key);

        if(value == null){
            return null;
        }

        try {
            return objectMapper.readValue(value, BookingReadModel.class);
        }catch (JacksonException e){
            throw new RuntimeException("Failed to parse Booking read model from Redis", e);
        }
    }

    public AvailabilityReadModel findAvailabilityById(UUID availabilityId) {
        String key = AVAILABILITY_KEY_PREFIX + availabilityId;
        String value = redisTemplate.opsForValue().get(key);

        if(value == null){
            return null;
        }

        try {
            return objectMapper.readValue(value, AvailabilityReadModel.class);
        }catch (JacksonException e){
            throw new RuntimeException("Failed to parse Availability read model from Redis", e);
        }
    }


}
