package com.example.AirbnbBookingSpring.saga;

import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class SagaEventConsumer {
    private final IEventConsumer eventConsumer;
    private final SagaEventProcessor sagaEventProcessor;
    
    @Scheduled(fixedDelay = 500)
    public void consumeEvents() {
        SagaEvent sagaEvent = eventConsumer.consume();

        if (sagaEvent != null) {
            sagaEventProcessor.processEvent(sagaEvent);
        }
    }

}
