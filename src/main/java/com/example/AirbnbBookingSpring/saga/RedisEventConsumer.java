package com.example.AirbnbBookingSpring.saga;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;
import tools.jackson.databind.ObjectMapper;

import java.util.concurrent.TimeUnit;

@Component
@RequiredArgsConstructor
@Slf4j
public class RedisEventConsumer implements IEventConsumer {
    private static final String SAGA_QUEUE = "saga:events";

    private final RedisTemplate<String, String> redisTemplate;
    private final ObjectMapper objectMapper;

    @Override
    public SagaEvent consume() {
        try {
            String eventJson = redisTemplate.opsForList().leftPop(SAGA_QUEUE, 1, TimeUnit.SECONDS);
            if (eventJson == null || eventJson.isEmpty()) {
                return null;
            }
            return objectMapper.readValue(eventJson, SagaEvent.class);
        } catch (Exception e) {
            throw new RuntimeException("Failed to consume saga event", e);
        }
    }
}

