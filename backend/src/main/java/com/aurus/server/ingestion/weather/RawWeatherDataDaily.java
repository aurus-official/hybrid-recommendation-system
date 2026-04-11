package com.aurus.server.ingestion.weather;

import java.util.List;

public class RawWeatherDataDaily {
    private List<String> time;

    private List<Float> precipitationSum;
    private List<Float> precipitationHours;
    private List<Integer> precipitationProbabilityMax;

    private List<Float> temperature2mMax;
    private List<Float> temperature2mMin;

    private List<Float> windSpeed10mMax;
    private List<Float> windGusts10mMax;

    private List<Float> et0FaoEvapotranspiration;

    public RawWeatherDataDaily() {
    }

    public RawWeatherDataDaily(List<String> time, List<Float> precipitationSum, List<Float> precipitationHours,
            List<Integer> precipitationProbabilityMax, List<Float> temperature2mMax, List<Float> temperature2mMin,
            List<Float> windSpeed10mMax, List<Float> windGusts10mMax, List<Float> et0FaoEvapotranspiration) {
        this.time = time;
        this.precipitationSum = precipitationSum;
        this.precipitationHours = precipitationHours;
        this.precipitationProbabilityMax = precipitationProbabilityMax;
        this.temperature2mMax = temperature2mMax;
        this.temperature2mMin = temperature2mMin;
        this.windSpeed10mMax = windSpeed10mMax;
        this.windGusts10mMax = windGusts10mMax;
        this.et0FaoEvapotranspiration = et0FaoEvapotranspiration;
    }

    public List<String> getTime() {
        return time;
    }

    public List<Float> getPrecipitationSum() {
        return precipitationSum;
    }

    public List<Float> getPrecipitationHours() {
        return precipitationHours;
    }

    public List<Integer> getPrecipitationProbabilityMax() {
        return precipitationProbabilityMax;
    }

    public List<Float> getTemperature2mMax() {
        return temperature2mMax;
    }

    public List<Float> getTemperature2mMin() {
        return temperature2mMin;
    }

    public List<Float> getWindSpeed10mMax() {
        return windSpeed10mMax;
    }

    public List<Float> getWindGusts10mMax() {
        return windGusts10mMax;
    }

    public List<Float> getEt0FaoEvapotranspiration() {
        return et0FaoEvapotranspiration;
    }

    public void setTime(List<String> time) {
        this.time = time;
    }

    public void setPrecipitationSum(List<Float> precipitationSum) {
        this.precipitationSum = precipitationSum;
    }

    public void setPrecipitationHours(List<Float> precipitationHours) {
        this.precipitationHours = precipitationHours;
    }

    public void setPrecipitationProbabilityMax(List<Integer> precipitationProbabilityMax) {
        this.precipitationProbabilityMax = precipitationProbabilityMax;
    }

    public void setTemperature2mMax(List<Float> temperature2mMax) {
        this.temperature2mMax = temperature2mMax;
    }

    public void setTemperature2mMin(List<Float> temperature2mMin) {
        this.temperature2mMin = temperature2mMin;
    }

    public void setWindSpeed10mMax(List<Float> windSpeed10mMax) {
        this.windSpeed10mMax = windSpeed10mMax;
    }

    public void setWindGusts10mMax(List<Float> windGusts10mMax) {
        this.windGusts10mMax = windGusts10mMax;
    }

    public void setEt0FaoEvapotranspiration(List<Float> et0FaoEvapotranspiration) {
        this.et0FaoEvapotranspiration = et0FaoEvapotranspiration;
    }
}
