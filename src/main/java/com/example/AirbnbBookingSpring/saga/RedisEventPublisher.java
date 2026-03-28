package com.example.AirbnbBookingSpring.saga;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;
import tools.jackson.databind.ObjectMapper;

@Component
@RequiredArgsConstructor
public class RedisEventPublisher implements IEventPublisher {
    @Value("${redis.saga.queue}")
    private String sagaQueue;

    private final RedisTemplate<String, String> redisTemplate;
    private final ObjectMapper objectMapper;

    @Override
    public void publish(SagaEvent sagaEvent) {
        try{
            String eventJson = objectMapper.writeValueAsString(sagaEvent);
            redisTemplate.opsForList().rightPush(sagaQueue, eventJson);
        }catch(Exception e){
            throw new RuntimeException("Failed to publish saga event", e);
        }
    }
}
