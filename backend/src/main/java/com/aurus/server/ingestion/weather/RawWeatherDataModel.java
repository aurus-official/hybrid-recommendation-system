package com.aurus.server.ingestion.weather;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.type.SqlTypes;

@Table(name = "raw_weather_data")
@Entity(name = "raw_weather_data")
public class RawWeatherDataModel {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    private float latitude;
    private float longitude;
    private float generationTimeMs;

    private int utcOffsetSeconds;
    private String timezone;
    private String timezoneAbbreviation;
    private float elevation;

    @JdbcTypeCode(SqlTypes.JSON)
    private RawWeatherDataHourlyUnits hourlyUnits;

    @JdbcTypeCode(SqlTypes.JSON)
    private RawWeatherDataHourly hourly;

    @JdbcTypeCode(SqlTypes.JSON)
    private RawWeatherDataDailyUnits dailyUnits;

    @JdbcTypeCode(SqlTypes.JSON)
    private RawWeatherDataDaily daily;

    public RawWeatherDataModel() {
    }

    public RawWeatherDataModel(float latitude, float longitude, float generationTimeMs, int utcOffsetSeconds,
            String timezone, String timezoneAbbreviation, float elevation, RawWeatherDataHourlyUnits hourlyUnits,
            RawWeatherDataHourly hourly, RawWeatherDataDailyUnits dailyUnits, RawWeatherDataDaily daily) {
        this.latitude = latitude;
        this.longitude = longitude;
        this.generationTimeMs = generationTimeMs;
        this.utcOffsetSeconds = utcOffsetSeconds;
        this.timezone = timezone;
        this.timezoneAbbreviation = timezoneAbbreviation;
        this.elevation = elevation;
        this.hourlyUnits = hourlyUnits;
        this.hourly = hourly;
        this.dailyUnits = dailyUnits;
        this.daily = daily;
    }

    public long getId() {
        return id;
    }

    public float getLatitude() {
        return latitude;
    }

    public float getLongitude() {
        return longitude;
    }

    public float getGenerationTimeMs() {
        return generationTimeMs;
    }

    public int getUtcOffsetSeconds() {
        return utcOffsetSeconds;
    }

    public String getTimezone() {
        return timezone;
    }

    public String getTimezoneAbbreviation() {
        return timezoneAbbreviation;
    }

    public float getElevation() {
        return elevation;
    }

    public RawWeatherDataHourlyUnits getHourlyUnits() {
        return hourlyUnits;
    }

    public RawWeatherDataHourly getHourly() {
        return hourly;
    }

    public RawWeatherDataDailyUnits getDailyUnits() {
        return dailyUnits;
    }

    public RawWeatherDataDaily getDaily() {
        return daily;
    }

}
