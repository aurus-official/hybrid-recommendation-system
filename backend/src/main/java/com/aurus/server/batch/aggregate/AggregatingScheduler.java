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
    @Scheduled(cron = "0 48 * * * *")
    public void aggregate() {
        LocalDateTime startingWindow = LocalDateTime.now().minus(2l, ChronoUnit.HOURS);
        LocalDateTime endingWindow = LocalDateTime.now();
        batchEventPublisher.publishAggregatingEvent(startingWindow, endingWindow);
        System.out.println("hello");
    }
}
