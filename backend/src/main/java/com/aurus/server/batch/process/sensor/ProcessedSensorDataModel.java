package com.aurus.server.batch.process.sensor;

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

    public ProcessedSensorDataModel() {
    }

    public ProcessedSensorDataModel(
            ProcessedSensorDataDTO soilTemp, ProcessedSensorDataDTO airTemp,
            ProcessedSensorDataDTO humidity, ProcessedSensorDataDTO pressure,
            ProcessedSensorDataDTO lux, ProcessedSensorDataDTO uv, ProcessedSensorDataDTO tds,
            ProcessedSensorDataDTO prongMoisture,
            ProcessedSensorDataDTO capacitiveMoisture, long rawSensorDataId) {

        this.soilTemp = soilTemp.value();
        this.soilTempUnit = soilTemp.unit();

        this.airTemp = airTemp.value();
        this.airTempUnit = airTemp.unit();

        this.humidity = humidity.value();
        this.humidityUnit = humidity.unit();

        this.pressure = pressure.value();
        this.pressureUnit = pressure.unit();

        this.lux = lux.value();
        this.luxUnit = lux.unit();

        this.uv = uv.value();
        this.uvUnit = uv.unit();

        this.tds = tds.value();
        this.tdsUnit = tds.unit();

        this.prongMoisture = prongMoisture.value();
        this.prongMoistureUnit = prongMoisture.unit();

        this.capacitiveMoisture = capacitiveMoisture.value();
        this.capacitiveMoistureUnit = capacitiveMoisture.unit();

        this.rawSensorDataId = rawSensorDataId;

    }

    public ProcessedSensorDataDTO getSoilTemp() {
        return new ProcessedSensorDataDTO(soilTemp, soilTempUnit);
    }

    public ProcessedSensorDataDTO getAirTemp() {
        return new ProcessedSensorDataDTO(airTemp, airTempUnit);
    }

    public ProcessedSensorDataDTO getHumidity() {
        return new ProcessedSensorDataDTO(humidity, humidityUnit);
    }

    public ProcessedSensorDataDTO getPressure() {
        return new ProcessedSensorDataDTO(pressure, pressureUnit);
    }

    public ProcessedSensorDataDTO getLux() {
        return new ProcessedSensorDataDTO(lux, luxUnit);
    }

    public ProcessedSensorDataDTO getUv() {
        return new ProcessedSensorDataDTO(uv, uvUnit);
    }

    public ProcessedSensorDataDTO getTds() {
        return new ProcessedSensorDataDTO(tds, tdsUnit);
    }

    public ProcessedSensorDataDTO getProngMoisture() {
        return new ProcessedSensorDataDTO(prongMoisture, prongMoistureUnit);
    }

    public ProcessedSensorDataDTO getCapacitiveMoisture() {
        return new ProcessedSensorDataDTO(capacitiveMoisture, capacitiveMoistureUnit);
    }

    public long getRawSensorDataId() {
        return rawSensorDataId;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

}
