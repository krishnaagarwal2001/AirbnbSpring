package com.example.AirbnbBookingSpring.saga;

import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;
import tools.jackson.databind.ObjectMapper;

@Component
@RequiredArgsConstructor
public class RedisEventPublisher implements IEventPublisher {

    private static final String SAGA_QUEUE = "saga:events";

    private final RedisTemplate<String, String> redisTemplate;
    private final ObjectMapper objectMapper;

    @Override
    public void publish(SagaEvent sagaEvent) {
        try{
            String eventJson = objectMapper.writeValueAsString(sagaEvent);
            redisTemplate.opsForList().rightPush(SAGA_QUEUE, eventJson);
        }catch(Exception e){
            throw new RuntimeException("Failed to publish saga event", e);
        }
    }
}
