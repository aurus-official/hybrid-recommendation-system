package com.aurus.server.batch;

import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;

@EnableScheduling
@Configuration
public class DerivingSchedule {

    private final BatchEventPublisher batchEventPublisher;

    public DerivingSchedule(BatchEventPublisher batchEventPublisher) {
        this.batchEventPublisher = batchEventPublisher;
    }

    @Scheduled(cron = "0 58 * * * *")
    public void deriving() {
        batchEventPublisher.publishDeriveEvent();

    }
}
