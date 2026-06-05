package com.aurus.server.batch.aggregate.weather;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

import com.aurus.server.batch.process.weather.ProcessedWeatherDataHourlyUnit;
import com.aurus.server.batch.process.weather.ProcessedWeatherDataModel;
import com.aurus.server.batch.process.weather.ProcessedWeatherDataPointHourly;

import org.jspecify.annotations.Nullable;
import org.springframework.batch.core.listener.StepExecutionListener;
import org.springframework.batch.infrastructure.item.ItemProcessor;

public class AggregatedWeatherDataProcessor
        implements ItemProcessor<ProcessedWeatherDataModel, AggregatedWeatherDataModel>, StepExecutionListener {

    @Override
    public @Nullable AggregatedWeatherDataModel process(ProcessedWeatherDataModel model) throws Exception {
        List<Float> stresses = model.getProcessedWeatherDataPointsHourly()
                .stream()
                .map(p -> tempStress((float) p.getTemperature()))
                .toList();

        float avgStress = (float) stresses.stream()
                .mapToDouble(Float::doubleValue)
                .average()
                .orElse(0.0);

        float maxStress = (float) stresses.stream()
                .mapToDouble(Float::doubleValue)
                .max()
                .orElse(0.0);

        float tempStressValue = 0.65f * avgStress +
                0.35f * maxStress;
        float humidityValue = average(model.getProcessedWeatherDataPointsHourly().stream()
                .map(ProcessedWeatherDataPointHourly::getHumidity).map(a -> (float) a).collect(Collectors.toList()));
        float vapourPressureDeficitValue = model.getProcessedWeatherDataPointsHourly().get(0)
                .getVapourPressureDeficit();
        float precipitationValue = model.getProcessedWeatherDataPointsHourly().stream()
                .map(ProcessedWeatherDataPointHourly::getPrecipitation).reduce(0f, (val, accu) -> (accu + val));
        float precipitationProbabilityValue = model.getProcessedWeatherDataPointsHourly().stream()
                .max(Comparator.comparingDouble(ProcessedWeatherDataPointHourly::getPrecipitationProbability)).get()
                .getPrecipitationProbability();
        float evapotranspirationValue = model.getProcessedWeatherDataPointsHourly().stream()
                .map(ProcessedWeatherDataPointHourly::getPrecipitation).reduce(0f, (val, accu) -> (accu + val));

        ProcessedWeatherDataHourlyUnit processedWeatherDataHourlyUnit = model.getProcessedWeatherDataHourlyUnit();

        AggregatedWeatherDataDTO tempStress = new AggregatedWeatherDataDTO(toFourDigitsDecimal(tempStressValue),
                "normalized");
        AggregatedWeatherDataDTO humidity = new AggregatedWeatherDataDTO(toFourDigitsDecimal(humidityValue),
                processedWeatherDataHourlyUnit.getHumidityUnit());
        AggregatedWeatherDataDTO vapourPressureDeficit = new AggregatedWeatherDataDTO(
                toFourDigitsDecimal(vapourPressureDeficitValue),
                processedWeatherDataHourlyUnit.getVapourPressureDeficitUnit());
        AggregatedWeatherDataDTO precipitation = new AggregatedWeatherDataDTO(
                toFourDigitsDecimal(precipitationValue),
                processedWeatherDataHourlyUnit.getPrecipitationUnit());
        AggregatedWeatherDataDTO precipitationProbability = new AggregatedWeatherDataDTO(
                toFourDigitsDecimal(precipitationProbabilityValue),
                processedWeatherDataHourlyUnit.getPrecipitationProbabilityUnit());
        AggregatedWeatherDataDTO evapotranspiration = new AggregatedWeatherDataDTO(
                toFourDigitsDecimal(evapotranspirationValue),
                processedWeatherDataHourlyUnit.getEvapotranspirationUnit());

        AggregatedWeatherDataModel aggregatedWeatherDataModel = new AggregatedWeatherDataModel(tempStress,
                humidity, vapourPressureDeficit, precipitation, precipitationProbability, evapotranspiration,
                model.getId());

        return aggregatedWeatherDataModel;
    }

    private float toFourDigitsDecimal(float value) {
        return Math.round(value * 10_000.0f) / 10_000.0f;
    }

    private float average(List<Float> pastProcessedWeatherDataValues) {
        return (float) pastProcessedWeatherDataValues.stream().mapToDouble(item -> item).average().getAsDouble();
    }

    private float tempStress(float tempValue) {

        float optimal = 26f;
        float scale = 80f;

        return clamp(1f - (float) Math.exp(
                -Math.pow((tempValue - optimal), 2) / scale));
    }

    private float clamp(float value) {
        return Math.max(0f, Math.min(1f, value));
    }
}
