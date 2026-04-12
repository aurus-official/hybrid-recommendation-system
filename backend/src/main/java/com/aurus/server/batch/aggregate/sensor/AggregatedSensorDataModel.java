package com.aurus.server.batch.aggregate.sensor;

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
            AggregatedSensorDataDTO soilTemp, AggregatedSensorDataDTO airTemp,
            AggregatedSensorDataDTO humidity, AggregatedSensorDataDTO pressure,
            AggregatedSensorDataDTO lux, AggregatedSensorDataDTO uv, AggregatedSensorDataDTO tds,
            AggregatedSensorDataDTO prongMoisture,
            AggregatedSensorDataDTO capacitiveMoisture,
            LocalDateTime startingWindow, LocalDateTime endingWindow) {

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

        this.startingWindow = startingWindow;
        this.endingWindow = endingWindow;
    }

    public AggregatedSensorDataDTO getSoilTemp() {
        return new AggregatedSensorDataDTO(soilTemp, soilTempUnit);
    }

    public AggregatedSensorDataDTO getAirTemp() {
        return new AggregatedSensorDataDTO(airTemp, airTempUnit);
    }

    public AggregatedSensorDataDTO getHumidity() {
        return new AggregatedSensorDataDTO(humidity, humidityUnit);
    }

    public AggregatedSensorDataDTO getPressure() {
        return new AggregatedSensorDataDTO(pressure, pressureUnit);
    }

    public AggregatedSensorDataDTO getLux() {
        return new AggregatedSensorDataDTO(lux, luxUnit);
    }

    public AggregatedSensorDataDTO getUv() {
        return new AggregatedSensorDataDTO(uv, uvUnit);
    }

    public AggregatedSensorDataDTO getTds() {
        return new AggregatedSensorDataDTO(tds, tdsUnit);
    }

    public AggregatedSensorDataDTO getProngMoisture() {
        return new AggregatedSensorDataDTO(prongMoisture, prongMoistureUnit);
    }

    public AggregatedSensorDataDTO getCapacitiveMoisture() {
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
