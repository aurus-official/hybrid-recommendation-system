package com.aurus.server.engine;

import com.aurus.server.batch.derive.sensor.DerivedSensorDataModel;
import com.aurus.server.batch.derive.weather.DerivedWeatherDataModel;

import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Component;

@Component
public class EngineEventPublisher {
    private final ApplicationEventPublisher applicationEventPublisher;

    public EngineEventPublisher(ApplicationEventPublisher applicationEventPublisher) {
        this.applicationEventPublisher = applicationEventPublisher;
    }

    public void publishDerivedSensorDataReadyEvent(DerivedSensorDataModel derivedSensorDataModel) {
        applicationEventPublisher.publishEvent(new EngineDerivedSensorDataReadyEvent(derivedSensorDataModel));

    }

    public void publishDerivedWeatherDataReadyEvent(DerivedWeatherDataModel derivedWeatherDataModel) {
        applicationEventPublisher.publishEvent(new EngineDerivedWeatherDataReadyEvent(derivedWeatherDataModel));
    }
}
