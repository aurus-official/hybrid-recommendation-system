package com.aurus.server.batch.aggregate.sensor;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;

import com.aurus.server.batch.BatchEventPublisher;

import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;

@EnableScheduling
@Configuration
public class AggregatingSensorDataScheduler {

    private final BatchEventPublisher batchEventPublisher;

    public AggregatingSensorDataScheduler(BatchEventPublisher batchEventPublisher) {
        this.batchEventPublisher = batchEventPublisher;
    }

    // @Scheduled(fixedDelay = 600_000l, initialDelay = 600_000l)
    @Scheduled(fixedDelay = 6_000_000l, initialDelay = 6_000_000l)
    public void aggregate() {
        LocalDateTime startingWindow = LocalDateTime.now().minus(10l, ChronoUnit.MINUTES);
        LocalDateTime endingWindow = LocalDateTime.now();
        batchEventPublisher.publishAggregatingSensorDataEvent(startingWindow, endingWindow);
    }
}
