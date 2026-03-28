package com.example.AirbnbBookingSpring.saga;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;
import tools.jackson.databind.ObjectMapper;

import java.util.concurrent.TimeUnit;

@Component
@RequiredArgsConstructor
@Slf4j
public class RedisEventConsumer implements IEventConsumer {
    @Value("${redis.saga.queue}")
    private String sagaQueue;

    private final RedisTemplate<String, String> redisTemplate;
    private final ObjectMapper objectMapper;

    @Override
    public SagaEvent consume() {
        try {
            String eventJson = redisTemplate.opsForList().leftPop(sagaQueue, 1, TimeUnit.SECONDS);
            if (eventJson == null || eventJson.isEmpty()) {
                return null;
            }
            return objectMapper.readValue(eventJson, SagaEvent.class);
        } catch (Exception e) {
            throw new RuntimeException("Failed to consume saga event", e);
        }
    }
}

