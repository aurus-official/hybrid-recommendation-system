package com.aurus.server.batch.aggregate;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

import com.aurus.server.batch.process.ProcessedSensorDataDTO;
import com.aurus.server.batch.process.ProcessedSensorDataModel;

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
        this.startingWindow = stepExecution.getJobParameters().getLocalDateTime("starting");
        this.endingWindow = stepExecution.getJobParameters().getLocalDateTime("ending");
    }

    @Override
    public @Nullable AggregatedSensorDataModel process(List<ProcessedSensorDataModel> models) throws Exception {
        float soilTempValue = average(models.stream().map(ProcessedSensorDataModel::getSoilTempStat)
                .map(ProcessedSensorDataDTO::value).collect(Collectors.toList()));

        float airTempValue = average(models.stream().map(ProcessedSensorDataModel::getAirTempStat)
                .map(ProcessedSensorDataDTO::value).collect(Collectors.toList()));

        float humidityValue = average(models.stream().map(ProcessedSensorDataModel::getHumidityStat)
                .map(ProcessedSensorDataDTO::value).collect(Collectors.toList()));

        float pressureValue = average(models.stream().map(ProcessedSensorDataModel::getPressureStat)
                .map(ProcessedSensorDataDTO::value).collect(Collectors.toList()));

        float luxValue = average(models.stream().map(ProcessedSensorDataModel::getLuxStat)
                .map(ProcessedSensorDataDTO::value).collect(Collectors.toList()));

        float uvValue = average(models.stream().map(ProcessedSensorDataModel::getUvStat)
                .map(ProcessedSensorDataDTO::value).collect(Collectors.toList()));

        float tdsValue = average(models.stream().map(ProcessedSensorDataModel::getTdsStat)
                .map(ProcessedSensorDataDTO::value).collect(Collectors.toList()));

        float prongMoistureValue = average(models.stream().map(ProcessedSensorDataModel::getProngMoistureStat)
                .map(ProcessedSensorDataDTO::value).collect(Collectors.toList()));

        float capacitiveMoistureValue = average(models.stream().map(ProcessedSensorDataModel::getCapacitiveMoistureStat)
                .map(ProcessedSensorDataDTO::value).collect(Collectors.toList()));

        AggregatedSensorDataDTO soilTempStat = new AggregatedSensorDataDTO(toFourDigitsDecimal(soilTempValue), "°C");
        AggregatedSensorDataDTO airTempStat = new AggregatedSensorDataDTO(toFourDigitsDecimal(airTempValue), "°C");
        AggregatedSensorDataDTO humidityStat = new AggregatedSensorDataDTO(toFourDigitsDecimal(humidityValue), "%RH");
        AggregatedSensorDataDTO pressureStat = new AggregatedSensorDataDTO(toFourDigitsDecimal(pressureValue), "°hPa");

        AggregatedSensorDataDTO luxStat = new AggregatedSensorDataDTO(toFourDigitsDecimal(luxValue), "lux");
        AggregatedSensorDataDTO uvStat = new AggregatedSensorDataDTO(toFourDigitsDecimal(uvValue), "index");
        AggregatedSensorDataDTO tdsStat = new AggregatedSensorDataDTO(toFourDigitsDecimal(tdsValue), "ppm");

        AggregatedSensorDataDTO prongMoistureStat = new AggregatedSensorDataDTO(toFourDigitsDecimal(prongMoistureValue),
                "%");
        AggregatedSensorDataDTO capacitiveMoistureStat = new AggregatedSensorDataDTO(
                toFourDigitsDecimal(capacitiveMoistureValue), "%");

        AggregatedSensorDataModel aggregatedSensorDataModel = new AggregatedSensorDataModel(soilTempStat, airTempStat,
                humidityStat, pressureStat, luxStat, uvStat, tdsStat, prongMoistureStat, capacitiveMoistureStat,
                startingWindow, endingWindow);

        return aggregatedSensorDataModel;
    }

    private float toFourDigitsDecimal(float value) {
        return Math.round(value * 10_000.0f) / 10_000.0f;
    }

    private float average(List<Float> pastRawSensorDataValues) {
        return (float) pastRawSensorDataValues.stream().mapToDouble(item -> item).average().getAsDouble();
    }

}
