package com.aurus.server.batch;

import java.time.LocalDateTime;

import com.aurus.server.batch.aggregate.sensor.AggregatingSensorDataEvent;
import com.aurus.server.batch.aggregate.weather.AggregatingWeatherDataEvent;
import com.aurus.server.batch.derive.sensor.DerivingSensorDataEvent;
import com.aurus.server.batch.process.sensor.ProcessingSensorDataEvent;
import com.aurus.server.batch.process.weather.ProcessingWeatherDataEvent;

import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Component;

@Component
public class BatchEventPublisher {

    private final ApplicationEventPublisher applicationEventPublisher;

    public BatchEventPublisher(ApplicationEventPublisher applicationEventPublisher) {
        this.applicationEventPublisher = applicationEventPublisher;
    }

    public void publishProcessingSensorDataEvent(Long id) {
        applicationEventPublisher.publishEvent(new ProcessingSensorDataEvent(id));
    }

    public void publishProcessingWeatherDataEvent(Long id) {
        applicationEventPublisher.publishEvent(new ProcessingWeatherDataEvent(id));
    }

    public void publishAggregatingSensorDataEvent(LocalDateTime startingWindow, LocalDateTime endingWindow) {
        applicationEventPublisher.publishEvent(new AggregatingSensorDataEvent(startingWindow, endingWindow));
    }

    public void publishAggregatingWeatherDataEvent(Long id) {
        applicationEventPublisher.publishEvent(new AggregatingWeatherDataEvent(id));

    }

    public void publishDerivingSensorDataEvent(Long id) {
        applicationEventPublisher.publishEvent(new DerivingSensorDataEvent(id));
    }
}
