package com.aurus.server.batch.process.weather;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import com.aurus.server.ingestion.weather.RawWeatherDataDaily;
import com.aurus.server.ingestion.weather.RawWeatherDataHourly;
import com.aurus.server.ingestion.weather.RawWeatherDataModel;

import org.jspecify.annotations.Nullable;
import org.springframework.batch.infrastructure.item.ItemProcessor;

public class ProcessedWeatherDataProcessor implements ItemProcessor<RawWeatherDataModel, ProcessedWeatherDataModel> {

    @Override
    public @Nullable ProcessedWeatherDataModel process(RawWeatherDataModel item) throws Exception {
        List<ProcessedWeatherDataPointHourly> processedWeatherDataPointsHourly = convertToProcessedWeatherHourlyList(
                item.getHourly());

        List<ProcessedWeatherDataPointDaily> processedWeatherDataPointsDaily = convertToProcessedWeatherDailyList(
                item.getDaily());

        ProcessedWeatherDataHourlyUnit processedWeatherDataHourlyUnit = new ProcessedWeatherDataHourlyUnit(
                item.getHourlyUnits().getTemperature2m(),
                item.getHourlyUnits().getRelativeHumidity2m(),
                item.getHourlyUnits().getPrecipitation(),
                item.getHourlyUnits().getPrecipitationProbability(),
                item.getHourlyUnits().getWindSpeed10m(),
                item.getHourlyUnits().getWindGusts10m(),
                item.getHourlyUnits().getVapourPressureDeficit(),
                item.getHourlyUnits().getEvapotranspiration(),
                item.getHourlyUnits().getSoilMoisture0To1cm());

        ProcessedWeatherDataDailyUnit processedWeatherDataDailyUnit = new ProcessedWeatherDataDailyUnit(
                item.getDailyUnits().getPrecipitationSum(),
                item.getDailyUnits().getPrecipitationHours(),
                item.getDailyUnits().getPrecipitationProbabilityMax(),
                item.getDailyUnits().getTemperature2mMax(),
                item.getDailyUnits().getTemperature2mMin(),
                item.getDailyUnits().getWindSpeed10mMax(),
                item.getDailyUnits().getWindGusts10mMax(),
                item.getDailyUnits().getEt0FaoEvapotranspiration());

        ProcessedWeatherDataModel processedWeatherDataModel = new ProcessedWeatherDataModel(
                item.getId(),
                processedWeatherDataPointsHourly,
                processedWeatherDataHourlyUnit,
                processedWeatherDataPointsDaily,
                processedWeatherDataDailyUnit);

        return processedWeatherDataModel;
    }

    private List<ProcessedWeatherDataPointHourly> convertToProcessedWeatherHourlyList(
            RawWeatherDataHourly rawWeatherDataHourly) {
        final int hourlyMaxCount = 12;
        List<ProcessedWeatherDataPointHourly> processedWeatherDataPointsHourly = new ArrayList<>();

        for (int i = 0; i < hourlyMaxCount; ++i) {
            ProcessedWeatherDataPointHourly temp = new ProcessedWeatherDataPointHourly(
                    LocalDateTime.parse(rawWeatherDataHourly.getTime().get(i)),
                    rawWeatherDataHourly.getApparentTemperature().get(i),
                    rawWeatherDataHourly.getRelativeHumidity2m().get(i),
                    rawWeatherDataHourly.getPrecipitation().get(i),
                    rawWeatherDataHourly.getPrecipitationProbability().get(i),
                    rawWeatherDataHourly.getWindSpeed10m().get(i),
                    rawWeatherDataHourly.getWindGusts10m().get(i),
                    rawWeatherDataHourly.getVapourPressureDeficit().get(i),
                    rawWeatherDataHourly.getEvapotranspiration().get(i),
                    rawWeatherDataHourly.getSoilMoisture0To1cm().get(i),
                    rawWeatherDataHourly.getSoilMoisture1To3cm().get(i),
                    rawWeatherDataHourly.getSoilMoisture3To9cm().get(i));

            processedWeatherDataPointsHourly.add(temp);
        }

        return processedWeatherDataPointsHourly;
    }

    private List<ProcessedWeatherDataPointDaily> convertToProcessedWeatherDailyList(
            RawWeatherDataDaily rawWeatherDataDaily) {
        final int dailyMaxCount = 7;
        List<ProcessedWeatherDataPointDaily> processedWeatherDataPointsDaily = new ArrayList<>();

        for (int i = 0; i < dailyMaxCount; ++i) {
            ProcessedWeatherDataPointDaily temp = new ProcessedWeatherDataPointDaily(
                    LocalDate.parse(rawWeatherDataDaily.getTime().get(i)),
                    rawWeatherDataDaily.getPrecipitationSum().get(i),
                    rawWeatherDataDaily.getPrecipitationHours().get(i),
                    rawWeatherDataDaily.getPrecipitationProbabilityMax().get(i),
                    rawWeatherDataDaily.getTemperature2mMax().get(i),
                    rawWeatherDataDaily.getTemperature2mMin().get(i),
                    rawWeatherDataDaily.getWindSpeed10mMax().get(i),
                    rawWeatherDataDaily.getWindGusts10mMax().get(i),
                    rawWeatherDataDaily.getEt0FaoEvapotranspiration().get(i));

            processedWeatherDataPointsDaily.add(temp);
        }

        return processedWeatherDataPointsDaily;
    }

}
