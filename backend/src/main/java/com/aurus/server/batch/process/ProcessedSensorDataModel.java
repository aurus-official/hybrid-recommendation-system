package com.aurus.server.batch.process;

import java.time.LocalDateTime;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

import org.hibernate.annotations.CreationTimestamp;

@Table(name = "processed_sensor_data")
@Entity(name = "processed_sensor_data")
public class ProcessedSensorDataModel {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    private float soilTemp;
    private String soilTempUnit;

    private float airTemp;
    private String airTempUnit;

    private float humidity;
    private String humidityUnit;

    private float pressure;
    private String pressureUnit;

    private float lux;
    private String luxUnit;

    private float uv;
    private String uvUnit;

    private float tds;
    private String tdsUnit;

    private float prongMoisture;
    private String prongMoistureUnit;

    private float capacitiveMoisture;
    private String capacitiveMoistureUnit;

    private long rawSensorDataId;

    @CreationTimestamp
    private LocalDateTime createdAt;

    public ProcessedSensorDataModel(
            ProcessedSensorDataDTO soilTempStat, ProcessedSensorDataDTO airTempStat,
            ProcessedSensorDataDTO humidityStat, ProcessedSensorDataDTO pressureStat,
            ProcessedSensorDataDTO luxStat, ProcessedSensorDataDTO uvStat, ProcessedSensorDataDTO tdsStat,
            ProcessedSensorDataDTO prongMoistureStat,
            ProcessedSensorDataDTO capacitiveMoistureStat, long rawSensorDataId) {

        this.soilTemp = soilTempStat.value();
        this.soilTempUnit = soilTempStat.unit();

        this.airTemp = airTempStat.value();
        this.airTempUnit = airTempStat.unit();

        this.humidity = humidityStat.value();
        this.humidityUnit = humidityStat.unit();

        this.pressure = pressureStat.value();
        this.pressureUnit = pressureStat.unit();

        this.lux = luxStat.value();
        this.luxUnit = luxStat.unit();

        this.uv = uvStat.value();
        this.uvUnit = uvStat.unit();

        this.tds = tdsStat.value();
        this.tdsUnit = tdsStat.unit();

        this.prongMoisture = prongMoistureStat.value();
        this.prongMoistureUnit = prongMoistureStat.unit();

        this.capacitiveMoisture = capacitiveMoistureStat.value();
        this.capacitiveMoistureUnit = capacitiveMoistureStat.unit();

        this.rawSensorDataId = rawSensorDataId;

    }

    public ProcessedSensorDataDTO getSoilTempStat() {
        return new ProcessedSensorDataDTO(soilTemp, soilTempUnit);
    }

    public ProcessedSensorDataDTO getAirTempStat() {
        return new ProcessedSensorDataDTO(airTemp, airTempUnit);
    }

    public ProcessedSensorDataDTO getHumidityStat() {
        return new ProcessedSensorDataDTO(humidity, humidityUnit);
    }

    public ProcessedSensorDataDTO getPressureStat() {
        return new ProcessedSensorDataDTO(pressure, pressureUnit);
    }

    public ProcessedSensorDataDTO getLuxStat() {
        return new ProcessedSensorDataDTO(lux, luxUnit);
    }

    public ProcessedSensorDataDTO getUvStat() {
        return new ProcessedSensorDataDTO(uv, uvUnit);
    }

    public ProcessedSensorDataDTO getTdsStat() {
        return new ProcessedSensorDataDTO(tds, tdsUnit);
    }

    public ProcessedSensorDataDTO getProngMoistureStat() {
        return new ProcessedSensorDataDTO(prongMoisture, prongMoistureUnit);
    }

    public ProcessedSensorDataDTO getCapacitiveMoistureStat() {
        return new ProcessedSensorDataDTO(capacitiveMoisture, capacitiveMoistureUnit);
    }

    public long getRawSensorDataId() {
        return rawSensorDataId;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

}
