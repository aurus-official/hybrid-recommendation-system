package com.aurus.server.batch.process.weather;

import java.time.LocalDate;

public class ProcessedWeatherDataPointDaily {
    private LocalDate date;
    private float precipitationSum;
    private float precipitationHours;
    private int precipitationProbabilityMax;

    private float temperature2mMax;
    private float temperature2mMin;

    private float windSpeed10mMax;
    private float windGusts10mMax;

    private float et0FaoEvapotranspiration;

    public ProcessedWeatherDataPointDaily() {
    }

    public ProcessedWeatherDataPointDaily(LocalDate date, float precipitationSum, float precipitationHours,
            int precipitationProbabilityMax, float temperature2mMax, float temperature2mMin, float windSpeed10mMax,
            float windGusts10mMax, float et0FaoEvapotranspiration) {
        this.date = date;
        this.precipitationSum = precipitationSum;
        this.precipitationHours = precipitationHours;
        this.precipitationProbabilityMax = precipitationProbabilityMax;
        this.temperature2mMax = temperature2mMax;
        this.temperature2mMin = temperature2mMin;
        this.windSpeed10mMax = windSpeed10mMax;
        this.windGusts10mMax = windGusts10mMax;
        this.et0FaoEvapotranspiration = et0FaoEvapotranspiration;
    }

    public LocalDate getDate() {
        return date;
    }

    public float getPrecipitationSum() {
        return precipitationSum;
    }

    public float getPrecipitationHours() {
        return precipitationHours;
    }

    public int getPrecipitationProbabilityMax() {
        return precipitationProbabilityMax;
    }

    public float getTemperature2mMax() {
        return temperature2mMax;
    }

    public float getTemperature2mMin() {
        return temperature2mMin;
    }

    public float getWindGusts10mMax() {
        return windGusts10mMax;
    }

    public float getWindSpeed10mMax() {
        return windSpeed10mMax;
    }

    public float getEt0FaoEvapotranspiration() {
        return et0FaoEvapotranspiration;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public void setPrecipitationSum(float precipitationSum) {
        this.precipitationSum = precipitationSum;
    }

    public void setPrecipitationHours(float precipitationHours) {
        this.precipitationHours = precipitationHours;
    }

    public void setPrecipitationProbabilityMax(int precipitationProbabilityMax) {
        this.precipitationProbabilityMax = precipitationProbabilityMax;
    }

    public void setTemperature2mMax(float temperature2mMax) {
        this.temperature2mMax = temperature2mMax;
    }

    public void setTemperature2mMin(float temperature2mMin) {
        this.temperature2mMin = temperature2mMin;
    }

    public void setWindSpeed10mMax(float windSpeed10mMax) {
        this.windSpeed10mMax = windSpeed10mMax;
    }

    public void setWindGusts10mMax(float windGusts10mMax) {
        this.windGusts10mMax = windGusts10mMax;
    }

    public void setEt0FaoEvapotranspiration(float et0FaoEvapotranspiration) {
        this.et0FaoEvapotranspiration = et0FaoEvapotranspiration;
    }

}
