package com.aurus.server.batch;

import java.time.LocalDateTime;

import com.aurus.server.batch.aggregate.AggregatingEvent;
import com.aurus.server.batch.process.ProcessingEvent;

import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Component;

@Component
public class BatchEventPublisher {

    private final ApplicationEventPublisher applicationEventPublisher;

    public BatchEventPublisher(ApplicationEventPublisher applicationEventPublisher) {
        this.applicationEventPublisher = applicationEventPublisher;
    }

    public void publishProcessingEvent(Long id) {
        applicationEventPublisher.publishEvent(new ProcessingEvent(id));
    }

    public void publishAggregatingEvent(LocalDateTime startingWindow, LocalDateTime endingWindow) {
        applicationEventPublisher.publishEvent(new AggregatingEvent(startingWindow, endingWindow));
    }
}
