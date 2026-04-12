package com.aurus.server.batch.aggregate.weather;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Table(name = "aggregated_sensor_data")
@Entity(name = "aggregated_sensor_data")
public class AggregatedWeatherDataModel {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    private float airTemp;
    private String airTempUnit;

    private float humidity;
    private String humidityUnit;

    private float windSpeed;
    private String windSpeedUnit;

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
            AggregatedWeatherDataDTO airTemp, AggregatedWeatherDataDTO humidity,
            AggregatedWeatherDataDTO windSpeed, AggregatedWeatherDataDTO precipitation,
            AggregatedWeatherDataDTO precipitationProbability,
            AggregatedWeatherDataDTO evapotranspiration, long processedWeatherDataId) {

        this.airTemp = airTemp.value();
        this.airTempUnit = airTemp.unit();

        this.humidity = humidity.value();
        this.humidityUnit = humidity.unit();

        this.windSpeed = windSpeed.value();
        this.windSpeedUnit = windSpeed.unit();

        this.precipitation = precipitation.value();
        this.precipitationUnit = precipitation.unit();

        this.precipitationProbability = precipitationProbability.value();
        this.precipitationProbabilityUnit = precipitationProbability.unit();

        this.evapotranspiration = evapotranspiration.value();
        this.evapotranspirationUnit = evapotranspiration.unit();

        this.processedWeatherDataId = processedWeatherDataId;

    }

    public AggregatedWeatherDataDTO getAirTemp() {
        return new AggregatedWeatherDataDTO(airTemp, airTempUnit);
    }

    public AggregatedWeatherDataDTO getHumidity() {
        return new AggregatedWeatherDataDTO(humidity, humidityUnit);
    }

    public AggregatedWeatherDataDTO getWindSpeed() {
        return new AggregatedWeatherDataDTO(windSpeed, windSpeedUnit);
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
