package com.aurus.server.batch;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

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

    public ProcessedSensorDataModel(
            SensorStat soilTempStat, SensorStat airTempStat, SensorStat humidityStat, SensorStat pressureStat,
            SensorStat luxStat, SensorStat uvStat, SensorStat tdsStat, SensorStat prongMoistureStat,
            SensorStat capacitiveMoistureStat, long rawSensorDataId) {

        this.soilTemp = soilTempStat.getValue();
        this.soilTempUnit = soilTempStat.getUnit();

        this.airTemp = airTempStat.getValue();
        this.airTempUnit = airTempStat.getUnit();

        this.humidity = humidityStat.getValue();
        this.humidityUnit = humidityStat.getUnit();

        this.pressure = pressureStat.getValue();
        this.pressureUnit = pressureStat.getUnit();

        this.lux = luxStat.getValue();
        this.luxUnit = luxStat.getUnit();

        this.uv = uvStat.getValue();
        this.uvUnit = uvStat.getUnit();

        this.tds = tdsStat.getValue();
        this.tdsUnit = tdsStat.getUnit();

        this.prongMoisture = prongMoistureStat.getValue();
        this.prongMoistureUnit = prongMoistureStat.getUnit();

        this.capacitiveMoisture = capacitiveMoistureStat.getValue();
        this.capacitiveMoistureUnit = capacitiveMoistureStat.getUnit();

        this.rawSensorDataId = rawSensorDataId;

    }

    public SensorStat getSoilTempStat() {
        return new SensorStat(soilTemp, soilTempUnit);
    }

    public SensorStat getAirTempStat() {
        return new SensorStat(airTemp, airTempUnit);
    }

    public SensorStat getHumidityStat() {
        return new SensorStat(humidity, humidityUnit);
    }

    public SensorStat getPressureStat() {
        return new SensorStat(pressure, pressureUnit);
    }

    public SensorStat getLuxStat() {
        return new SensorStat(lux, luxUnit);
    }

    public SensorStat getUvStat() {
        return new SensorStat(uv, uvUnit);
    }

    public SensorStat getTdsStat() {
        return new SensorStat(tds, tdsUnit);
    }

    public SensorStat getProngMoistureStat() {
        return new SensorStat(prongMoisture, prongMoistureUnit);
    }

    public SensorStat getCapacitiveMoistureStat() {
        return new SensorStat(capacitiveMoisture, capacitiveMoistureUnit);
    }

    public long getRawSensorDataId() {
        return rawSensorDataId;
    }

}
