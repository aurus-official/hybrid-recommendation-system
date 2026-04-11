package com.aurus.server.batch.process.weather;

import java.time.LocalDateTime;

public class ProcessedWeatherDataPointHourly {

    private LocalDateTime time;
    private float temperature;
    private int humidity;
    private float precipitation;
    private int precipitationProbability;
    private float windSpeed;
    private float windGusts;
    private float vapourPressureDeficit;
    private float evapotranspiration;
    private float soilMoisture0_1cm;
    private float soilMoisture1_3cm;
    private float soilMoisture3_9cm;

    public ProcessedWeatherDataPointHourly() {
    }

    public ProcessedWeatherDataPointHourly(LocalDateTime time, float temperature, int humidity, float precipitation,
            int precipitationProbability, float windSpeed, float windGusts, float vapourPressureDeficit,
            float evapotranspiration, float soilMoisture0_1cm, float soilMoisture1_3cm, float soilMoisture3_9cm) {
        this.time = time;
        this.temperature = temperature;
        this.humidity = humidity;
        this.precipitation = precipitation;
        this.precipitationProbability = precipitationProbability;
        this.windSpeed = windSpeed;
        this.windGusts = windGusts;
        this.vapourPressureDeficit = vapourPressureDeficit;
        this.evapotranspiration = evapotranspiration;
        this.soilMoisture0_1cm = soilMoisture0_1cm;
        this.soilMoisture1_3cm = soilMoisture1_3cm;
        this.soilMoisture3_9cm = soilMoisture3_9cm;
    }

    public LocalDateTime getTime() {
        return time;
    }

    public float getTemperature() {
        return temperature;
    }

    public int getHumidity() {
        return humidity;
    }

    public float getPrecipitation() {
        return precipitation;
    }

    public int getPrecipitationProbability() {
        return precipitationProbability;
    }

    public float getWindSpeed() {
        return windSpeed;
    }

    public float getWindGusts() {
        return windGusts;
    }

    public float getVapourPressureDeficit() {
        return vapourPressureDeficit;
    }

    public float getEvapotranspiration() {
        return evapotranspiration;
    }

    public float getSoilMoisture0_1cm() {
        return soilMoisture0_1cm;
    }

    public float getSoilMoisture1_3cm() {
        return soilMoisture1_3cm;
    }

    public float getSoilMoisture3_9cm() {
        return soilMoisture3_9cm;
    }

    public void setTime(LocalDateTime time) {
        this.time = time;
    }

    public void setTemperature(float temperature) {
        this.temperature = temperature;
    }

    public void setHumidity(int humidity) {
        this.humidity = humidity;
    }

    public void setPrecipitation(float precipitation) {
        this.precipitation = precipitation;
    }

    public void setPrecipitationProbability(int precipitationProbability) {
        this.precipitationProbability = precipitationProbability;
    }

    public void setWindSpeed(float windSpeed) {
        this.windSpeed = windSpeed;
    }

    public void setWindGusts(float windGusts) {
        this.windGusts = windGusts;
    }

    public void setVapourPressureDeficit(float vapourPressureDeficit) {
        this.vapourPressureDeficit = vapourPressureDeficit;
    }

    public void setEvapotranspiration(float evapotranspiration) {
        this.evapotranspiration = evapotranspiration;
    }

    public void setSoilMoisture0_1cm(float soilMoisture0_1cm) {
        this.soilMoisture0_1cm = soilMoisture0_1cm;
    }

    public void setSoilMoisture1_3cm(float soilMoisture1_3cm) {
        this.soilMoisture1_3cm = soilMoisture1_3cm;
    }

    public void setSoilMoisture3_9cm(float soilMoisture3_9cm) {
        this.soilMoisture3_9cm = soilMoisture3_9cm;
    }
}
