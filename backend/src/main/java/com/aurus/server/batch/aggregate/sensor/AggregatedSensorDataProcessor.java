package com.aurus.server.batch.aggregate.sensor;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

import com.aurus.server.batch.process.sensor.ProcessedSensorDataDTO;
import com.aurus.server.batch.process.sensor.ProcessedSensorDataModel;

import org.jspecify.annotations.Nullable;
import org.springframework.batch.core.listener.StepExecutionListener;
import org.springframework.batch.core.step.StepExecution;
import org.springframework.batch.infrastructure.item.ItemProcessor;

public class AggregatedSensorDataProcessor
        implements ItemProcessor<List<ProcessedSensorDataModel>, AggregatedSensorDataModel>, StepExecutionListener {

    private LocalDateTime startingWindow;
    private LocalDateTime endingWindow;

    @Override
    public void beforeStep(StepExecution stepExecution) {
        this.startingWindow = stepExecution.getJobParameters().getLocalDateTime("startingWindow");
        this.endingWindow = stepExecution.getJobParameters().getLocalDateTime("endingWindow");
    }

    @Override
    public @Nullable AggregatedSensorDataModel process(List<ProcessedSensorDataModel> models) throws Exception {

        float soilTempValue = average(models.stream().map(ProcessedSensorDataModel::getSoilTemp)
                .map(ProcessedSensorDataDTO::value).collect(Collectors.toList()));

        float airTempValue = average(models.stream().map(ProcessedSensorDataModel::getAirTemp)
                .map(ProcessedSensorDataDTO::value).collect(Collectors.toList()));

        float humidityValue = average(models.stream().map(ProcessedSensorDataModel::getHumidity)
                .map(ProcessedSensorDataDTO::value).collect(Collectors.toList()));

        float pressureValue = average(models.stream().map(ProcessedSensorDataModel::getPressure)
                .map(ProcessedSensorDataDTO::value).collect(Collectors.toList()));

        float luxValue = average(models.stream().map(ProcessedSensorDataModel::getLux)
                .map(ProcessedSensorDataDTO::value).collect(Collectors.toList()));

        float uvValue = average(models.stream().map(ProcessedSensorDataModel::getUv)
                .map(ProcessedSensorDataDTO::value).collect(Collectors.toList()));

        float tdsValue = average(models.stream().map(ProcessedSensorDataModel::getTds)
                .map(ProcessedSensorDataDTO::value).collect(Collectors.toList()));

        float prongMoistureValue = average(models.stream().map(ProcessedSensorDataModel::getProngMoisture)
                .map(ProcessedSensorDataDTO::value).collect(Collectors.toList()));

        float capacitiveMoistureValue = average(models.stream().map(ProcessedSensorDataModel::getCapacitiveMoisture)
                .map(ProcessedSensorDataDTO::value).collect(Collectors.toList()));

        AggregatedSensorDataDTO soilTemp = new AggregatedSensorDataDTO(toFourDigitsDecimal(soilTempValue), "°C");
        AggregatedSensorDataDTO airTemp = new AggregatedSensorDataDTO(toFourDigitsDecimal(airTempValue), "°C");
        AggregatedSensorDataDTO humidity = new AggregatedSensorDataDTO(toFourDigitsDecimal(humidityValue), "%RH");
        AggregatedSensorDataDTO pressure = new AggregatedSensorDataDTO(toFourDigitsDecimal(pressureValue), "hPa");

        AggregatedSensorDataDTO lux = new AggregatedSensorDataDTO(toFourDigitsDecimal(luxValue), "lux");
        AggregatedSensorDataDTO uv = new AggregatedSensorDataDTO(toFourDigitsDecimal(uvValue), "index");
        AggregatedSensorDataDTO tds = new AggregatedSensorDataDTO(toFourDigitsDecimal(tdsValue), "ppm");

        AggregatedSensorDataDTO prongMoisture = new AggregatedSensorDataDTO(toFourDigitsDecimal(prongMoistureValue),
                "%");
        AggregatedSensorDataDTO capacitiveMoisture = new AggregatedSensorDataDTO(
                toFourDigitsDecimal(capacitiveMoistureValue), "%");

        AggregatedSensorDataModel aggregatedSensorDataModel = new AggregatedSensorDataModel(soilTemp, airTemp,
                humidity, pressure, lux, uv, tds, prongMoisture, capacitiveMoisture,
                startingWindow, endingWindow);

        return aggregatedSensorDataModel;
    }

    private float toFourDigitsDecimal(float value) {
        return Math.round(value * 10_000.0f) / 10_000.0f;
    }

    private float average(List<Float> pastProcessedSensorDataValues) {
        return (float) pastProcessedSensorDataValues.stream().mapToDouble(item -> item).average().getAsDouble();
    }

}
