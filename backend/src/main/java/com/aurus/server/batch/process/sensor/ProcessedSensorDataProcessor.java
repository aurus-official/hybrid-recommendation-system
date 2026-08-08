package com.aurus.server.batch.process.sensor;

import java.util.ArrayDeque;
import java.util.Comparator;
import java.util.Deque;
import java.util.List;
import java.util.stream.Collectors;

import com.aurus.server.ingestion.sensor.RawSensorDataModel;
import com.aurus.server.ingestion.sensor.RawSensorDataRepository;

import org.jspecify.annotations.Nullable;
import org.springframework.batch.infrastructure.item.ItemProcessor;

public class ProcessedSensorDataProcessor implements ItemProcessor<RawSensorDataModel, ProcessedSensorDataModel> {

    private final RawSensorDataRepository rawSensorDataRepository;

    public ProcessedSensorDataProcessor(RawSensorDataRepository rawSensorDataRepository) {
        this.rawSensorDataRepository = rawSensorDataRepository;
    }

    @Override
    public @Nullable ProcessedSensorDataModel process(RawSensorDataModel item) throws Exception {
        final float uvScalingFactor = 5.5f;
        final float tdsMaxOutput = 2.3f;
        final float prongMoistureDry = 3.5f;
        final float prongMoistureWet = 0f;
        final float capacitiveMoistureDry = 2.8f;
        final float capacitiveMoistureWet = 0f;

        Deque<RawSensorDataModel> pastRawSensorDataModels = new ArrayDeque<>(rawSensorDataRepository
                .findTwoPastRawSensorDataModels(item.getId()));

        pastRawSensorDataModels.addFirst(item);

        float soilTempValue = (item.getSoilTemp() == -1f) ? -1f
                : movingAverage(pastRawSensorDataModels.stream()
                        .map(RawSensorDataModel::getSoilTemp).collect(Collectors.toList()).reversed());
        float airTempValue = (item.getAirTemp() == -1f) ? -1f
                : movingAverage(pastRawSensorDataModels.stream()
                        .map(RawSensorDataModel::getAirTemp).collect(Collectors.toList()).reversed());
        float humidityValue = (item.getHumidity() == -1f) ? -1f
                : movingAverage(pastRawSensorDataModels.stream()
                        .map(RawSensorDataModel::getHumidity).collect(Collectors.toList()).reversed());
        float pressureValue = (item.getPressure() == -1f) ? -1f
                : movingAverage(pastRawSensorDataModels.stream()
                        .map(RawSensorDataModel::getPressure).collect(Collectors.toList()).reversed());
        float luxValue = (item.getLux() == -1f) ? -1f
                : median(pastRawSensorDataModels.stream()
                        .sorted(Comparator.comparingDouble(RawSensorDataModel::getLux)).map(RawSensorDataModel::getLux)
                        .collect(Collectors.toList()));
        float uvValue = (item.getUvVolts() == -1f) ? -1f
                : median(pastRawSensorDataModels.stream()
                        .sorted(Comparator.comparingDouble(RawSensorDataModel::getUvVolts))
                        .map(RawSensorDataModel::getUvVolts)
                        .collect(Collectors.toList())) * uvScalingFactor;
        float tdsValue = (item.getTdsVolts() == -1f) ? -1f
                : (float) (Math.pow(median(pastRawSensorDataModels.stream()
                        .sorted(Comparator.comparingDouble(RawSensorDataModel::getTdsVolts))
                        .map(RawSensorDataModel::getTdsVolts)
                        .collect(Collectors.toList())) / tdsMaxOutput, 1.5f) * 1000f)
                        / (1f + 0.02f * (soilTempValue - 25f));

        float prongMoistureValue = (item.getProngMoisture() == -1f) ? -1f
                : Math.max(0.0f,
                        Math.min(100,
                                (prongMoistureDry
                                        - average(pastRawSensorDataModels.stream()
                                                .map(RawSensorDataModel::getProngMoisture)
                                                .collect(Collectors.toList())))
                                        / (prongMoistureDry - prongMoistureWet)))
                        * 100f;

        float capacitiveMoistureValue = (item.getCapacitiveMoisture() == -1f) ? -1f
                : Math.max(0.0f, Math.min(100, (capacitiveMoistureDry - average(
                        pastRawSensorDataModels.stream().map(RawSensorDataModel::getCapacitiveMoisture)
                                .collect(Collectors.toList())))
                        / (capacitiveMoistureDry - capacitiveMoistureWet))) * 100f;

        ProcessedSensorDataDTO soilTemp = new ProcessedSensorDataDTO(toFourDigitsDecimal(soilTempValue), "°C");
        ProcessedSensorDataDTO airTemp = new ProcessedSensorDataDTO(toFourDigitsDecimal(airTempValue), "°C");
        ProcessedSensorDataDTO humidity = new ProcessedSensorDataDTO(toFourDigitsDecimal(humidityValue), "%RH");
        ProcessedSensorDataDTO pressure = new ProcessedSensorDataDTO(toFourDigitsDecimal(pressureValue), "hPa");

        ProcessedSensorDataDTO lux = new ProcessedSensorDataDTO(toFourDigitsDecimal(luxValue), "lux");
        ProcessedSensorDataDTO uv = new ProcessedSensorDataDTO(toFourDigitsDecimal(uvValue), "index");
        ProcessedSensorDataDTO tds = new ProcessedSensorDataDTO(toFourDigitsDecimal(tdsValue), "ppm");

        ProcessedSensorDataDTO prongMoisture = new ProcessedSensorDataDTO(toFourDigitsDecimal(prongMoistureValue),
                "%");
        ProcessedSensorDataDTO capacitiveMoisture = new ProcessedSensorDataDTO(
                toFourDigitsDecimal(capacitiveMoistureValue), "%");

        ProcessedSensorDataModel processedSensorDataModel = new ProcessedSensorDataModel(soilTemp, airTemp,
                humidity, pressure, lux, uv, tds, prongMoisture, capacitiveMoisture,
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
