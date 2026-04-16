package com.aurus.server.engine;

import java.util.concurrent.atomic.AtomicBoolean;

import com.aurus.server.batch.derive.sensor.DerivedSensorDataModel;
import com.aurus.server.batch.derive.weather.DerivedWeatherDataModel;

import org.springframework.context.event.EventListener;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.stereotype.Component;

@Component
@EnableAsync
public class EngineEventListener {
    private final AtomicBoolean isDerivedSensorDataReady = new AtomicBoolean(false);
    private final AtomicBoolean isDerivedWeatherDataReady = new AtomicBoolean(false);
    private final EngineService engineService;
    private DerivedSensorDataModel derivedSensorDataModel;
    private DerivedWeatherDataModel derivedWeatherDataModel;

    public EngineEventListener(EngineService engineService) {
        this.engineService = engineService;
    }

    @EventListener
    @Async
    public void listenDerivedSensorDataReadyEvent(EngineDerivedSensorDataReadyEvent engineDerivedSensorDataReadyEvent) {
        isDerivedSensorDataReady.set(true);
        this.derivedSensorDataModel = engineDerivedSensorDataReadyEvent.derivedSensorDataModel();
        checkAndStartEngine();
    }

    @EventListener
    @Async
    public void listenDerivedWeatherDataReadyEvent(
            EngineDerivedWeatherDataReadyEvent engineDerivedWeatherDataReadyEvent) {
        isDerivedWeatherDataReady.set(true);
        this.derivedWeatherDataModel = engineDerivedWeatherDataReadyEvent.derivedWeatherDataModel();
        checkAndStartEngine();
    }

    @Async
    private synchronized void checkAndStartEngine() {
        if (isDerivedSensorDataReady.get() && isDerivedWeatherDataReady.get()) {
            engineService.startEngine(this.derivedSensorDataModel, this.derivedWeatherDataModel);
            isDerivedSensorDataReady.set(false);
            isDerivedWeatherDataReady.set(false);
        }
    }
}
