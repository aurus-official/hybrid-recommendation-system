package com.aurus.server.batch.aggregate;

import java.time.LocalDateTime;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Table(name = "aggregated_sensor_data")
@Entity(name = "aggregated_sensor_data")
public class AggregatedSensorDataModel {

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

    private LocalDateTime startingWindow;
    private LocalDateTime endingWindow;

    public AggregatedSensorDataModel() {
    }

    public AggregatedSensorDataModel(
            AggregatedSensorDataDTO soilTempStat, AggregatedSensorDataDTO airTempStat,
            AggregatedSensorDataDTO humidityStat, AggregatedSensorDataDTO pressureStat,
            AggregatedSensorDataDTO luxStat, AggregatedSensorDataDTO uvStat, AggregatedSensorDataDTO tdsStat,
            AggregatedSensorDataDTO prongMoistureStat,
            AggregatedSensorDataDTO capacitiveMoistureStat,
            LocalDateTime startingWindow, LocalDateTime endingWindow) {

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

        this.startingWindow = startingWindow;
        this.endingWindow = endingWindow;
    }

    public AggregatedSensorDataDTO getSoilTempStat() {
        return new AggregatedSensorDataDTO(soilTemp, soilTempUnit);
    }

    public AggregatedSensorDataDTO getAirTempStat() {
        return new AggregatedSensorDataDTO(airTemp, airTempUnit);
    }

    public AggregatedSensorDataDTO getHumidityStat() {
        return new AggregatedSensorDataDTO(humidity, humidityUnit);
    }

    public AggregatedSensorDataDTO getPressureStat() {
        return new AggregatedSensorDataDTO(pressure, pressureUnit);
    }

    public AggregatedSensorDataDTO getLuxStat() {
        return new AggregatedSensorDataDTO(lux, luxUnit);
    }

    public AggregatedSensorDataDTO getUvStat() {
        return new AggregatedSensorDataDTO(uv, uvUnit);
    }

    public AggregatedSensorDataDTO getTdsStat() {
        return new AggregatedSensorDataDTO(tds, tdsUnit);
    }

    public AggregatedSensorDataDTO getProngMoistureStat() {
        return new AggregatedSensorDataDTO(prongMoisture, prongMoistureUnit);
    }

    public AggregatedSensorDataDTO getCapacitiveMoistureStat() {
        return new AggregatedSensorDataDTO(capacitiveMoisture, capacitiveMoistureUnit);
    }

    public long getId() {
        return id;
    }

    public LocalDateTime getStartingWindow() {
        return startingWindow;
    }

    public LocalDateTime getEndingWindow() {
        return endingWindow;
    }

}
