package com.aurus.server.ingestion.weather;

public class RawWeatherDataHourlyUnits {
    private String time;
    private String temperature2m;
    private String relativeHumidity2m;
    private String apparentTemperature;
    private String precipitation;
    private String precipitationProbability;
    private String windSpeed10m;
    private String windGusts10m;
    private String vapourPressureDeficit;
    private String evapotranspiration;
    private String soilMoisture0To1cm;
    private String soilMoisture1To3cm;
    private String soilMoisture3To9cm;

    public RawWeatherDataHourlyUnits() {
    }

    public RawWeatherDataHourlyUnits(String time, String temperature2m, String relativeHumidity2m,
            String apparentTemperature, String precipitation, String precipitationProbability, String windSpeed10m,
            String windGusts10m, String vapourPressureDeficit, String evapotranspiration, String soilMoisture0To1cm,
            String soilMoisture1To3cm, String soilMoisture3To9cm) {
        this.time = time;
        this.temperature2m = temperature2m;
        this.relativeHumidity2m = relativeHumidity2m;
        this.apparentTemperature = apparentTemperature;
        this.precipitation = precipitation;
        this.precipitationProbability = precipitationProbability;
        this.windSpeed10m = windSpeed10m;
        this.windGusts10m = windGusts10m;
        this.vapourPressureDeficit = vapourPressureDeficit;
        this.evapotranspiration = evapotranspiration;
        this.soilMoisture0To1cm = soilMoisture0To1cm;
        this.soilMoisture1To3cm = soilMoisture1To3cm;
        this.soilMoisture3To9cm = soilMoisture3To9cm;
    }

    public String getTime() {
        return time;
    }

    public String getTemperature2m() {
        return temperature2m;
    }

    public String getRelativeHumidity2m() {
        return relativeHumidity2m;
    }

    public String getApparentTemperature() {
        return apparentTemperature;
    }

    public String getPrecipitation() {
        return precipitation;
    }

    public String getPrecipitationProbability() {
        return precipitationProbability;
    }

    public String getWindGusts10m() {
        return windGusts10m;
    }

    public String getWindSpeed10m() {
        return windSpeed10m;
    }

    public String getVapourPressureDeficit() {
        return vapourPressureDeficit;
    }

    public String getEvapotranspiration() {
        return evapotranspiration;
    }

    public String getSoilMoisture0To1cm() {
        return soilMoisture0To1cm;
    }

    public String getSoilMoisture1To3cm() {
        return soilMoisture1To3cm;
    }

    public String getSoilMoisture3To9cm() {
        return soilMoisture3To9cm;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public void setTemperature2m(String temperature2m) {
        this.temperature2m = temperature2m;
    }

    public void setRelativeHumidity2m(String relativeHumidity2m) {
        this.relativeHumidity2m = relativeHumidity2m;
    }

    public void setApparentTemperature(String apparentTemperature) {
        this.apparentTemperature = apparentTemperature;
    }

    public void setPrecipitation(String precipitation) {
        this.precipitation = precipitation;
    }

    public void setPrecipitationProbability(String precipitationProbability) {
        this.precipitationProbability = precipitationProbability;
    }

    public void setWindGusts10m(String windGusts10m) {
        this.windGusts10m = windGusts10m;
    }

    public void setWindSpeed10m(String windSpeed10m) {
        this.windSpeed10m = windSpeed10m;
    }

    public void setVapourPressureDeficit(String vapourPressureDeficit) {
        this.vapourPressureDeficit = vapourPressureDeficit;
    }

    public void setEvapotranspiration(String evapotranspiration) {
        this.evapotranspiration = evapotranspiration;
    }

    public void setSoilMoisture0To1cm(String soilMoisture0To1cm) {
        this.soilMoisture0To1cm = soilMoisture0To1cm;
    }

    public void setSoilMoisture1To3cm(String soilMoisture1To3cm) {
        this.soilMoisture1To3cm = soilMoisture1To3cm;
    }

    public void setSoilMoisture3To9cm(String soilMoisture3To9cm) {
        this.soilMoisture3To9cm = soilMoisture3To9cm;
    }

}
