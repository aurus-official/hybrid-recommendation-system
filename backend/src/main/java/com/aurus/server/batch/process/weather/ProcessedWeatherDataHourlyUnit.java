package com.aurus.server.batch.process.weather;

public class ProcessedWeatherDataHourlyUnit {
    private String temperatureUnit;
    private String humidityUnit;
    private String precipitationUnit;
    private String precipitationProbabilityUnit;
    private String windSpeedUnit;
    private String windGustsUnit;
    private String vapourPressureDeficitUnit;
    private String evapotranspirationUnit;
    private String soilMoistureUnit;

    public ProcessedWeatherDataHourlyUnit() {
    }

    public ProcessedWeatherDataHourlyUnit(String temperatureUnit, String humidityUnit, String precipitationUnit,
            String precipitationProbabilityUnit, String windSpeedUnit, String windGustsUnit,
            String vapourPressureDeficitUnit, String evapotranspirationUnit, String soilMoistureUnit) {
        this.temperatureUnit = temperatureUnit;
        this.humidityUnit = humidityUnit;
        this.precipitationUnit = precipitationUnit;
        this.precipitationProbabilityUnit = precipitationProbabilityUnit;
        this.windSpeedUnit = windSpeedUnit;
        this.windGustsUnit = windGustsUnit;
        this.vapourPressureDeficitUnit = vapourPressureDeficitUnit;
        this.evapotranspirationUnit = evapotranspirationUnit;
        this.soilMoistureUnit = soilMoistureUnit;
    }

    public String getTemperatureUnit() {
        return temperatureUnit;
    }

    public String getHumidityUnit() {
        return humidityUnit;
    }

    public String getPrecipitationUnit() {
        return precipitationUnit;
    }

    public String getPrecipitationProbabilityUnit() {
        return precipitationProbabilityUnit;
    }

    public String getWindSpeedUnit() {
        return windSpeedUnit;
    }

    public String getWindGustsUnit() {
        return windGustsUnit;
    }

    public String getVapourPressureDeficitUnit() {
        return vapourPressureDeficitUnit;
    }

    public String getEvapotranspirationUnit() {
        return evapotranspirationUnit;
    }

    public String getSoilMoistureUnit() {
        return soilMoistureUnit;
    }

    public void setTemperatureUnit(String temperatureUnit) {
        this.temperatureUnit = temperatureUnit;
    }

    public void setHumidityUnit(String humidityUnit) {
        this.humidityUnit = humidityUnit;
    }

    public void setPrecipitationUnit(String precipitationUnit) {
        this.precipitationUnit = precipitationUnit;
    }

    public void setPrecipitationProbabilityUnit(String precipitationProbabilityUnit) {
        this.precipitationProbabilityUnit = precipitationProbabilityUnit;
    }

    public void setWindSpeedUnit(String windSpeedUnit) {
        this.windSpeedUnit = windSpeedUnit;
    }

    public void setWindGustsUnit(String windGustsUnit) {
        this.windGustsUnit = windGustsUnit;
    }

    public void setVapourPressureDeficitUnit(String vapourPressureDeficitUnit) {
        this.vapourPressureDeficitUnit = vapourPressureDeficitUnit;
    }

    public void setEvapotranspirationUnit(String evapotranspirationUnit) {
        this.evapotranspirationUnit = evapotranspirationUnit;
    }

    public void setSoilMoistureUnit(String soilMoistureUnit) {
        this.soilMoistureUnit = soilMoistureUnit;
    }
}
