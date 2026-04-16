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

    @Scheduled(cron = "0 14 * * * *")
    // @Scheduled(cron = "0 48 * * * *")
    public void aggregate() {
        LocalDateTime startingWindow = LocalDateTime.now().minus(24l, ChronoUnit.HOURS);
        LocalDateTime endingWindow = LocalDateTime.now();
        batchEventPublisher.publishAggregatingSensorDataEvent(startingWindow, endingWindow);
    }
}
