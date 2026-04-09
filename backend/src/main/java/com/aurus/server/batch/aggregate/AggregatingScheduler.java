package com.aurus.server.batch.aggregate;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;

import com.aurus.server.batch.BatchEventPublisher;

import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;

@EnableScheduling
@Configuration
public class AggregatingScheduler {

    private final BatchEventPublisher batchEventPublisher;

    public AggregatingScheduler(BatchEventPublisher batchEventPublisher) {
        this.batchEventPublisher = batchEventPublisher;
    }

    // @Scheduled(cron = "0 5 * * * *")
    @Scheduled(cron = "0 52 * * * *")
    public void aggregate() {
        LocalDateTime startingWindow = LocalDateTime.now();
        LocalDateTime endingWindow = LocalDateTime.now().minus(1l, ChronoUnit.HOURS);
        batchEventPublisher.publishAggregatingEvent(startingWindow, endingWindow);
    }
}
