package com.aurus.server.batch.process.weather;

public class ProcessedWeatherDataDailyUnit {
    private String precipitationSumUnit;
    private String precipitationHoursUnit;
    private String precipitationProbabilityMaxUnit;

    private String temperature2mMaxUnit;
    private String temperature2mMinUnit;

    private String windSpeed10mMaxUnit;
    private String windGusts10mMaxUnit;

    private String et0FaoEvapotranspirationUnit;

    public ProcessedWeatherDataDailyUnit() {
    }

    public ProcessedWeatherDataDailyUnit(String precipitationSumUnit, String precipitationHoursUnit,
            String precipitationProbabilityMaxUnit, String temperature2mMaxUnit, String temperature2mMinUnit,
            String windSpeed10mMaxUnit, String windGusts10mMaxUnit, String et0FaoEvapotranspirationUnit) {
        this.precipitationSumUnit = precipitationSumUnit;
        this.precipitationHoursUnit = precipitationHoursUnit;
        this.precipitationProbabilityMaxUnit = precipitationProbabilityMaxUnit;
        this.temperature2mMaxUnit = temperature2mMaxUnit;
        this.temperature2mMinUnit = temperature2mMinUnit;
        this.windSpeed10mMaxUnit = windSpeed10mMaxUnit;
        this.windGusts10mMaxUnit = windGusts10mMaxUnit;
        this.et0FaoEvapotranspirationUnit = et0FaoEvapotranspirationUnit;
    }

    public String getPrecipitationSumUnit() {
        return precipitationSumUnit;
    }

    public String getPrecipitationHoursUnit() {
        return precipitationHoursUnit;
    }

    public String getPrecipitationProbabilityMaxUnit() {
        return precipitationProbabilityMaxUnit;
    }

    public String getTemperature2mMaxUnit() {
        return temperature2mMaxUnit;
    }

    public String getTemperature2mMinUnit() {
        return temperature2mMinUnit;
    }

    public String getWindSpeed10mMaxUnit() {
        return windSpeed10mMaxUnit;
    }

    public String getWindGusts10mMaxUnit() {
        return windGusts10mMaxUnit;
    }

    public String getEt0FaoEvapotranspirationUnit() {
        return et0FaoEvapotranspirationUnit;
    }

    public void setPrecipitationSumUnit(String precipitationSumUnit) {
        this.precipitationSumUnit = precipitationSumUnit;
    }

    public void setPrecipitationHoursUnit(String precipitationHoursUnit) {
        this.precipitationHoursUnit = precipitationHoursUnit;
    }

    public void setPrecipitationProbabilityMaxUnit(String precipitationProbabilityMaxUnit) {
        this.precipitationProbabilityMaxUnit = precipitationProbabilityMaxUnit;
    }

    public void setTemperature2mMaxUnit(String temperature2mMaxUnit) {
        this.temperature2mMaxUnit = temperature2mMaxUnit;
    }

    public void setTemperature2mMinUnit(String temperature2mMinUnit) {
        this.temperature2mMinUnit = temperature2mMinUnit;
    }

    public void setWindSpeed10mMaxUnit(String windSpeed10mMaxUnit) {
        this.windSpeed10mMaxUnit = windSpeed10mMaxUnit;
    }

    public void setWindGusts10mMaxUnit(String windGusts10mMaxUnit) {
        this.windGusts10mMaxUnit = windGusts10mMaxUnit;
    }

    public void setEt0FaoEvapotranspirationUnit(String et0FaoEvapotranspirationUnit) {
        this.et0FaoEvapotranspirationUnit = et0FaoEvapotranspirationUnit;
    }

}
