package com.aurus.server.batch.aggregate.weather;

import com.aurus.server.batch.process.weather.ProcessedWeatherDataHourlyUnit;
import com.aurus.server.batch.process.weather.ProcessedWeatherDataModel;

import org.jspecify.annotations.Nullable;
import org.springframework.batch.core.listener.StepExecutionListener;
import org.springframework.batch.infrastructure.item.ItemProcessor;

public class AggregatedWeatherDataProcessor
        implements ItemProcessor<ProcessedWeatherDataModel, AggregatedWeatherDataModel>, StepExecutionListener {

    @Override
    public @Nullable AggregatedWeatherDataModel process(ProcessedWeatherDataModel model) throws Exception {
        float airTempValue = model.getProcessedWeatherDataPointsHourly().get(0).getTemperature();
        float humidityValue = model.getProcessedWeatherDataPointsHourly().get(0).getHumidity();
        float windSpeedValue = model.getProcessedWeatherDataPointsHourly().get(0).getWindSpeed();
        float precipitationValue = model.getProcessedWeatherDataPointsHourly().get(0).getPrecipitation();
        float precipitationProbabilityValue = model.getProcessedWeatherDataPointsHourly().get(0)
                .getPrecipitationProbability();
        float evapotranspirationValue = model.getProcessedWeatherDataPointsHourly().get(0).getEvapotranspiration();

        ProcessedWeatherDataHourlyUnit processedWeatherDataHourlyUnit = model.getProcessedWeatherDataHourlyUnit();

        AggregatedWeatherDataDTO airTempStat = new AggregatedWeatherDataDTO(toFourDigitsDecimal(airTempValue),
                processedWeatherDataHourlyUnit.getTemperatureUnit());
        AggregatedWeatherDataDTO humidityStat = new AggregatedWeatherDataDTO(toFourDigitsDecimal(humidityValue),
                processedWeatherDataHourlyUnit.getHumidityUnit());
        AggregatedWeatherDataDTO windSpeedStat = new AggregatedWeatherDataDTO(toFourDigitsDecimal(windSpeedValue),
                processedWeatherDataHourlyUnit.getWindSpeedUnit());
        AggregatedWeatherDataDTO precipitationStat = new AggregatedWeatherDataDTO(
                toFourDigitsDecimal(precipitationValue),
                processedWeatherDataHourlyUnit.getPrecipitationUnit());
        AggregatedWeatherDataDTO precipitationProbabilityStat = new AggregatedWeatherDataDTO(
                toFourDigitsDecimal(precipitationProbabilityValue),
                processedWeatherDataHourlyUnit.getPrecipitationProbabilityUnit());
        AggregatedWeatherDataDTO evapotranspirationStat = new AggregatedWeatherDataDTO(
                toFourDigitsDecimal(evapotranspirationValue),
                processedWeatherDataHourlyUnit.getEvapotranspirationUnit());

        AggregatedWeatherDataModel aggregatedWeatherDataModel = new AggregatedWeatherDataModel(airTempStat,
                humidityStat, windSpeedStat, precipitationStat, precipitationProbabilityStat, evapotranspirationStat,
                model.getId());

        return aggregatedWeatherDataModel;
    }

    private float toFourDigitsDecimal(float value) {
        return Math.round(value * 10_000.0f) / 10_000.0f;
    }
}
