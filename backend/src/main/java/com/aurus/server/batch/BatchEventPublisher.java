package com.aurus.server.batch;

import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Component;

@Component
public class BatchEventPublisher {

    private final ApplicationEventPublisher applicationEventPublisher;

    public BatchEventPublisher(ApplicationEventPublisher applicationEventPublisher) {
        this.applicationEventPublisher = applicationEventPublisher;

    }

    public void publishSmoothingEvent(Long id) {
        applicationEventPublisher.publishEvent(new SmoothingEvent(id));
    }

    // public void publishDeriveEvent(Long id) {
    // applicationEventPublisher.publishEvent(new SmoothingEvent(id));
    // }
}
