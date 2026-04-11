package com.aurus.server.ingestion.weather;

public class RawWeatherDataDailyUnits {
    private String time;
    private String precipitationSum;
    private String precipitationHours;
    private String precipitationProbabilityMax;
    private String temperature2mMax;
    private String temperature2mMin;
    private String windSpeed10mMax;
    private String windGusts10mMax;
    private String et0FaoEvapotranspiration;

    public RawWeatherDataDailyUnits() {
    }

    public RawWeatherDataDailyUnits(String time, String precipitationSum, String precipitationHours,
            String precipitationProbabilityMax, String temperature2mMax, String temperature2mMin,
            String windSpeed10mMax, String windGusts10mMax, String et0FaoEvapotranspiration) {
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

    public String getTime() {
        return time;
    }

    public String getPrecipitationSum() {
        return precipitationSum;
    }

    public String getPrecipitationHours() {
        return precipitationHours;
    }

    public String getPrecipitationProbabilityMax() {
        return precipitationProbabilityMax;
    }

    public String getTemperature2mMax() {
        return temperature2mMax;
    }

    public String getTemperature2mMin() {
        return temperature2mMin;
    }

    public String getWindSpeed10mMax() {
        return windSpeed10mMax;
    }

    public String getWindGusts10mMax() {
        return windGusts10mMax;
    }

    public String getEt0FaoEvapotranspiration() {
        return et0FaoEvapotranspiration;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public void setPrecipitationSum(String precipitationSum) {
        this.precipitationSum = precipitationSum;
    }

    public void setPrecipitationHours(String precipitationHours) {
        this.precipitationHours = precipitationHours;
    }

    public void setPrecipitationProbabilityMax(String precipitationProbabilityMax) {
        this.precipitationProbabilityMax = precipitationProbabilityMax;
    }

    public void setTemperature2mMax(String temperature2mMax) {
        this.temperature2mMax = temperature2mMax;
    }

    public void setTemperature2mMin(String temperature2mMin) {
        this.temperature2mMin = temperature2mMin;
    }

    public void setWindGusts10mMax(String windGusts10mMax) {
        this.windGusts10mMax = windGusts10mMax;
    }

    public void setWindSpeed10mMax(String windSpeed10mMax) {
        this.windSpeed10mMax = windSpeed10mMax;
    }

    public void setEt0FaoEvapotranspiration(String et0FaoEvapotranspiration) {
        this.et0FaoEvapotranspiration = et0FaoEvapotranspiration;
    }

}
