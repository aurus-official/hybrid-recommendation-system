package com.aurus.server.batch.process.weather;

import java.time.LocalDateTime;
import java.util.List;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.type.SqlTypes;

@Table(name = "processed_weather_data")
@Entity(name = "processed_weather_data")
public class ProcessedWeatherDataModel {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    @CreationTimestamp
    private LocalDateTime createdAt;

    private long rawWeatherDataId;

    @JdbcTypeCode(SqlTypes.JSON)
    private List<ProcessedWeatherDataPointHourly> processedWeatherDataPointsHourly;

    @JdbcTypeCode(SqlTypes.JSON)
    private ProcessedWeatherDataHourlyUnit processedWeatherDataHourlyUnit;

    @JdbcTypeCode(SqlTypes.JSON)
    private List<ProcessedWeatherDataPointDaily> processedWeatherDataPointsDaily;

    @JdbcTypeCode(SqlTypes.JSON)
    private ProcessedWeatherDataDailyUnit processedWeatherDataDailyUnit;

    public ProcessedWeatherDataModel(long rawWeatherDataId,
            List<ProcessedWeatherDataPointHourly> processedWeatherDataPointsHourly,
            ProcessedWeatherDataHourlyUnit processedWeatherDataHourlyUnit,
            List<ProcessedWeatherDataPointDaily> processedWeatherDataPointsDaily,
            ProcessedWeatherDataDailyUnit processedWeatherDataDailyUnit) {
        this.rawWeatherDataId = rawWeatherDataId;
        this.processedWeatherDataPointsHourly = processedWeatherDataPointsHourly;
        this.processedWeatherDataHourlyUnit = processedWeatherDataHourlyUnit;
        this.processedWeatherDataPointsDaily = processedWeatherDataPointsDaily;
        this.processedWeatherDataDailyUnit = processedWeatherDataDailyUnit;
    }

    public long getId() {
        return id;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public long getRawWeatherDataId() {
        return rawWeatherDataId;
    }

    public List<ProcessedWeatherDataPointHourly> getProcessedWeatherDataPointsHourly() {
        return processedWeatherDataPointsHourly;
    }

    public ProcessedWeatherDataHourlyUnit getProcessedWeatherDataHourlyUnit() {
        return processedWeatherDataHourlyUnit;
    }

    public List<ProcessedWeatherDataPointDaily> getProcessedWeatherDataPointsDaily() {
        return processedWeatherDataPointsDaily;
    }

    public ProcessedWeatherDataDailyUnit getProcessedWeatherDataDailyUnit() {
        return processedWeatherDataDailyUnit;
    }

    public void setProcessedWeatherDataPointsHourly(
            List<ProcessedWeatherDataPointHourly> processedWeatherDataPointsHourly) {
        this.processedWeatherDataPointsHourly = processedWeatherDataPointsHourly;
    }

    public void setProcessedWeatherDataHourlyUnit(ProcessedWeatherDataHourlyUnit processedWeatherDataHourlyUnit) {
        this.processedWeatherDataHourlyUnit = processedWeatherDataHourlyUnit;
    }

    public void setProcessedWeatherDataPointsDaily(
            List<ProcessedWeatherDataPointDaily> processedWeatherDataPointsDaily) {
        this.processedWeatherDataPointsDaily = processedWeatherDataPointsDaily;
    }

    public void setProcessedWeatherDataDailyUnit(ProcessedWeatherDataDailyUnit processedWeatherDataDailyUnit) {
        this.processedWeatherDataDailyUnit = processedWeatherDataDailyUnit;
    }
}
