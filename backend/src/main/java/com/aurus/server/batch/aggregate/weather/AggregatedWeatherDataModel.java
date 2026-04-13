package com.aurus.server.batch.aggregate.weather;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Table(name = "aggregated_weather_data")
@Entity(name = "aggregated_weather_data")
public class AggregatedWeatherDataModel {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    private float tempStress;
    private String tempStressUnit;

    private float humidity;
    private String humidityUnit;

    private float vapourPressureDeficit;
    private String vapourPressureDeficitUnit;

    private float precipitation;
    private String precipitationUnit;

    private float precipitationProbability;
    private String precipitationProbabilityUnit;

    private float evapotranspiration;
    private String evapotranspirationUnit;

    private long processedWeatherDataId;

    public AggregatedWeatherDataModel() {
    }

    public AggregatedWeatherDataModel(
            AggregatedWeatherDataDTO tempStress, AggregatedWeatherDataDTO humidity,
            AggregatedWeatherDataDTO vapourPressureDeficit, AggregatedWeatherDataDTO precipitation,
            AggregatedWeatherDataDTO precipitationProbability,
            AggregatedWeatherDataDTO evapotranspiration, long processedWeatherDataId) {

        this.tempStress = tempStress.value();
        this.tempStressUnit = tempStress.unit();

        this.humidity = humidity.value();
        this.humidityUnit = humidity.unit();

        this.vapourPressureDeficit = vapourPressureDeficit.value();
        this.vapourPressureDeficitUnit = vapourPressureDeficit.unit();

        this.precipitation = precipitation.value();
        this.precipitationUnit = precipitation.unit();

        this.precipitationProbability = precipitationProbability.value();
        this.precipitationProbabilityUnit = precipitationProbability.unit();

        this.evapotranspiration = evapotranspiration.value();
        this.evapotranspirationUnit = evapotranspiration.unit();

        this.processedWeatherDataId = processedWeatherDataId;

    }

    public AggregatedWeatherDataDTO getTempStress() {
        return new AggregatedWeatherDataDTO(tempStress, tempStressUnit);
    }

    public AggregatedWeatherDataDTO getHumidity() {
        return new AggregatedWeatherDataDTO(humidity, humidityUnit);
    }

    public AggregatedWeatherDataDTO getVapourPressureDeficit() {
        return new AggregatedWeatherDataDTO(vapourPressureDeficit, vapourPressureDeficitUnit);
    }

    public AggregatedWeatherDataDTO getPrecipitation() {
        return new AggregatedWeatherDataDTO(precipitation, precipitationUnit);
    }

    public AggregatedWeatherDataDTO getPrecipitationProbability() {
        return new AggregatedWeatherDataDTO(precipitationProbability, precipitationProbabilityUnit);
    }

    public AggregatedWeatherDataDTO getEvapotranspiration() {
        return new AggregatedWeatherDataDTO(evapotranspiration, evapotranspirationUnit);
    }

    public long getProcessedWeatherDataId() {
        return processedWeatherDataId;
    }

    public long getId() {
        return id;
    }
}
