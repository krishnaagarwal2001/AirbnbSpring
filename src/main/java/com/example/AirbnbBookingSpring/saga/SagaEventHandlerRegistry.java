package com.example.AirbnbBookingSpring.saga;

import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.function.Function;
import java.util.stream.Collectors;

@Component
public class SagaEventHandlerRegistry {
    private final Map<SagaEventType, SagaEventHandler> sagaEventHandlers;

    public SagaEventHandlerRegistry(List<SagaEventHandler> handlers) {
        this.sagaEventHandlers = handlers.stream()
                .collect(Collectors.toUnmodifiableMap(SagaEventHandler::eventType, Function.identity()));
    }

    public Optional<SagaEventHandler> getSagaEventHandler(SagaEventType eventType){
        return Optional.ofNullable(sagaEventHandlers.get(eventType));
    }

}