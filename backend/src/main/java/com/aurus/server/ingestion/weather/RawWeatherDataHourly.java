package com.aurus.server.ingestion.weather;

import java.util.List;

public class RawWeatherDataHourly {

    private List<String> time;

    private List<Float> temperature2m;
    private List<Integer> relativeHumidity2m;
    private List<Float> apparentTemperature;

    private List<Float> precipitation;
    private List<Integer> precipitationProbability;

    private List<Float> windSpeed10m;
    private List<Float> windGusts10m;

    private List<Float> vapourPressureDeficit;
    private List<Float> evapotranspiration;

    private List<Float> soilMoisture0To1cm;
    private List<Float> soilMoisture1To3cm;
    private List<Float> soilMoisture3To9cm;

    public RawWeatherDataHourly() {
    }

    public RawWeatherDataHourly(List<String> time, List<Float> temperature2m, List<Integer> relativeHumidity2m,
            List<Float> apparentTemperature, List<Float> precipitation, List<Integer> precipitationProbability,
            List<Float> windSpeed10m, List<Float> windGusts10m, List<Float> vapourPressureDeficit,
            List<Float> evapotranspiration, List<Float> soilMoisture0To1cm, List<Float> soilMoisture1To3cm,
            List<Float> soilMoisture3To9cm) {
        this.time = time;
        this.temperature2m = temperature2m;
        this.relativeHumidity2m = relativeHumidity2m;
        this.apparentTemperature = apparentTemperature;
        this.precipitation = precipitation;
        this.precipitationProbability = precipitationProbability;
        this.windGusts10m = windGusts10m;
        this.windSpeed10m = windSpeed10m;
        this.vapourPressureDeficit = vapourPressureDeficit;
        this.evapotranspiration = evapotranspiration;
        this.soilMoisture0To1cm = soilMoisture0To1cm;
        this.soilMoisture1To3cm = soilMoisture1To3cm;
        this.soilMoisture3To9cm = soilMoisture3To9cm;
    }

    public List<String> getTime() {
        return time;
    }

    public List<Float> getTemperature2m() {
        return temperature2m;
    }

    public List<Integer> getRelativeHumidity2m() {
        return relativeHumidity2m;
    }

    public List<Float> getApparentTemperature() {
        return apparentTemperature;
    }

    public List<Float> getPrecipitation() {
        return precipitation;
    }

    public List<Integer> getPrecipitationProbability() {
        return precipitationProbability;
    }

    public List<Float> getWindGusts10m() {
        return windGusts10m;
    }

    public List<Float> getWindSpeed10m() {
        return windSpeed10m;
    }

    public List<Float> getVapourPressureDeficit() {
        return vapourPressureDeficit;
    }

    public List<Float> getEvapotranspiration() {
        return evapotranspiration;
    }

    public List<Float> getSoilMoisture0To1cm() {
        return soilMoisture0To1cm;
    }

    public List<Float> getSoilMoisture1To3cm() {
        return soilMoisture1To3cm;
    }

    public List<Float> getSoilMoisture3To9cm() {
        return soilMoisture3To9cm;
    }

    public void setTime(List<String> time) {
        this.time = time;
    }

    public void setTemperature2m(List<Float> temperature2m) {
        this.temperature2m = temperature2m;
    }

    public void setRelativeHumidity2m(List<Integer> relativeHumidity2m) {
        this.relativeHumidity2m = relativeHumidity2m;
    }

    public void setApparentTemperature(List<Float> apparentTemperature) {
        this.apparentTemperature = apparentTemperature;
    }

    public void setPrecipitation(List<Float> precipitation) {
        this.precipitation = precipitation;
    }

    public void setPrecipitationProbability(List<Integer> precipitationProbability) {
        this.precipitationProbability = precipitationProbability;
    }

    public void setWindGusts10m(List<Float> windGusts10m) {
        this.windGusts10m = windGusts10m;
    }

    public void setWindSpeed10m(List<Float> windSpeed10m) {
        this.windSpeed10m = windSpeed10m;
    }

    public void setVapourPressureDeficit(List<Float> vapourPressureDeficit) {
        this.vapourPressureDeficit = vapourPressureDeficit;
    }

    public void setEvapotranspiration(List<Float> evapotranspiration) {
        this.evapotranspiration = evapotranspiration;
    }

    public void setSoilMoisture0To1cm(List<Float> soilMoisture0To1cm) {
        this.soilMoisture0To1cm = soilMoisture0To1cm;
    }

    public void setSoilMoisture1To3cm(List<Float> soilMoisture1To3cm) {
        this.soilMoisture1To3cm = soilMoisture1To3cm;
    }

    public void setSoilMoisture3To9cm(List<Float> soilMoisture3To9cm) {
        this.soilMoisture3To9cm = soilMoisture3To9cm;
    }

}
