package com.aurus.server.batch;

import java.util.ArrayDeque;
import java.util.Comparator;
import java.util.Deque;
import java.util.List;
import java.util.stream.Collectors;

import com.aurus.server.ingestion.RawSensorDataModel;
import com.aurus.server.ingestion.RawSensorDataRepository;

import org.jspecify.annotations.Nullable;
import org.springframework.batch.infrastructure.item.ItemProcessor;

public class SmoothingProcessor implements ItemProcessor<RawSensorDataModel, ProcessedSensorDataModel> {

    private final RawSensorDataRepository rawSensorDataRepository;

    public SmoothingProcessor(RawSensorDataRepository rawSensorDataRepository) {
        this.rawSensorDataRepository = rawSensorDataRepository;
    }

    @Override
    public @Nullable ProcessedSensorDataModel process(RawSensorDataModel item) throws Exception {
        final float uvScalingFactor = 5.5f;
        final float ecCalibrationFactor = 320f;
        final float tdsCalibrationFactor = 0.6f;
        final float prongMoistureDry = 3.3f;
        final float prongMoistureWet = 2.0f;
        final float capacitiveMoistureDry = 2.25f;
        final float capacitiveMoistureWet = 0.8f;

        Deque<RawSensorDataModel> pastRawSensorDataModels = new ArrayDeque<>(rawSensorDataRepository
                .findTwoPastRawSensorDataModels(item.getId()));

        pastRawSensorDataModels.addFirst(item);

        float soilTempValue = movingAverage(pastRawSensorDataModels.stream()
                .map(RawSensorDataModel::getSoilTemp).collect(Collectors.toList()).reversed());
        float airTempValue = movingAverage(pastRawSensorDataModels.stream()
                .map(RawSensorDataModel::getAirTemp).collect(Collectors.toList()).reversed());
        float humidityValue = movingAverage(pastRawSensorDataModels.stream()
                .map(RawSensorDataModel::getHumidity).collect(Collectors.toList()).reversed());
        float pressureValue = movingAverage(pastRawSensorDataModels.stream()
                .map(RawSensorDataModel::getPressure).collect(Collectors.toList()).reversed());
        float luxValue = median(pastRawSensorDataModels.stream()
                .sorted(Comparator.comparingDouble(RawSensorDataModel::getLux)).map(RawSensorDataModel::getLux)
                .collect(Collectors.toList()));
        float uvValue = median(pastRawSensorDataModels.stream()
                .sorted(Comparator.comparingDouble(RawSensorDataModel::getUvVolts)).map(RawSensorDataModel::getUvVolts)
                .collect(Collectors.toList())) * uvScalingFactor;
        float tdsValue = median(pastRawSensorDataModels.stream()
                .sorted(Comparator.comparingDouble(RawSensorDataModel::getTdsVolts))
                .map(RawSensorDataModel::getTdsVolts)
                .collect(Collectors.toList())) * ecCalibrationFactor * tdsCalibrationFactor;
        float prongMoistureValue = Math.max(0.0f,
                Math.min(100,
                        (prongMoistureDry
                                - average(pastRawSensorDataModels.stream().map(RawSensorDataModel::getProngMoisture)
                                        .collect(Collectors.toList())))
                                / (prongMoistureDry - prongMoistureWet)));

        float capacitiveMoistureValue = Math.max(0.0f, Math.min(100, (capacitiveMoistureDry - average(
                pastRawSensorDataModels.stream().map(RawSensorDataModel::getCapacitiveMoisture)
                        .collect(Collectors.toList())))
                / (capacitiveMoistureDry - capacitiveMoistureWet)));

        SensorStat soilTempStat = new SensorStat(toFourDigitsDecimal(soilTempValue), "°C");
        SensorStat airTempStat = new SensorStat(toFourDigitsDecimal(airTempValue), "°C");
        SensorStat humidityStat = new SensorStat(toFourDigitsDecimal(humidityValue), "%RH");
        SensorStat pressureStat = new SensorStat(toFourDigitsDecimal(pressureValue), "°hPa");

        SensorStat luxStat = new SensorStat(toFourDigitsDecimal(luxValue), "lux");
        SensorStat uvStat = new SensorStat(toFourDigitsDecimal(uvValue), "index");
        SensorStat tdsStat = new SensorStat(toFourDigitsDecimal(tdsValue), "ppm");

        SensorStat prongMoistureStat = new SensorStat(toFourDigitsDecimal(prongMoistureValue), "%");
        SensorStat capacitiveMoistureStat = new SensorStat(toFourDigitsDecimal(capacitiveMoistureValue), "%");

        ProcessedSensorDataModel processedSensorDataModel = new ProcessedSensorDataModel(soilTempStat, airTempStat,
                humidityStat, pressureStat, luxStat, uvStat, tdsStat, prongMoistureStat, capacitiveMoistureStat,
                item.getId());

        return processedSensorDataModel;
    }

    private float movingAverage(List<Float> pastRawSensorDataValues) {
        float lastMovingAverageValue = -1f;
        float alpha = 0.4f;

        for (Float rawSensorDataValue : pastRawSensorDataValues) {
            if (lastMovingAverageValue < 0) {
                lastMovingAverageValue = rawSensorDataValue;
                continue;
            }

            lastMovingAverageValue = ((alpha * rawSensorDataValue) + ((1.0f - alpha) * lastMovingAverageValue));
        }

        return lastMovingAverageValue;

    }

    private float median(List<Float> pastRawSensorDataValues) {
        int midPoint = (pastRawSensorDataValues.size() - 1) / 2;
        if (pastRawSensorDataValues.size() % 2 == 0) {
            return (pastRawSensorDataValues.get(midPoint) + pastRawSensorDataValues.get(midPoint + 1)) / 2;
        }

        return pastRawSensorDataValues.get(midPoint);
    }

    private float average(List<Float> pastRawSensorDataValues) {
        return (float) pastRawSensorDataValues.stream().mapToDouble(item -> item).average().getAsDouble();
    }

    private float toFourDigitsDecimal(float value) {
        return Math.round(value * 10_000.0f) / 10_000.0f;
    }
}
