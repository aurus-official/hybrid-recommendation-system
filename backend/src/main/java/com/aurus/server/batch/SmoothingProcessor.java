package com.aurus.server.batch;

import com.aurus.server.ingestion.RawSensorDataModel;

import org.jspecify.annotations.Nullable;
import org.springframework.batch.infrastructure.item.ItemProcessor;

public class SmoothingProcessor implements ItemProcessor<RawSensorDataModel, ProcessedSensorDataModel> {

    // FIX : Change values and units
    @Override
    public @Nullable ProcessedSensorDataModel process(RawSensorDataModel item) throws Exception {
        SensorStat soilTempStat = new SensorStat(item.getSoilTemp(), "°C");
        SensorStat airTempStat = new SensorStat(item.getAirTemp(), "°C");
        SensorStat humidityStat = new SensorStat(item.getSoilTemp(), "°C");
        SensorStat pressureStat = new SensorStat(item.getSoilTemp(), "°C");
        SensorStat luxStat = new SensorStat(item.getSoilTemp(), "°C");
        SensorStat uvStat = new SensorStat(item.getSoilTemp(), "°C");
        SensorStat tdsStat = new SensorStat(item.getSoilTemp(), "°C");
        SensorStat prongMoistureStat = new SensorStat(item.getSoilTemp(), "°C");
        SensorStat capacitiveMoistureStat = new SensorStat(item.getSoilTemp(), "°C");

        ProcessedSensorDataModel processedSensorDataModel = new ProcessedSensorDataModel(soilTempStat, airTempStat,
                humidityStat, pressureStat, luxStat, uvStat, tdsStat, prongMoistureStat, capacitiveMoistureStat,
                item.getId());

        return processedSensorDataModel;
    }
}
