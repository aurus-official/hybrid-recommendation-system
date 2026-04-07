package com.aurus.server.ingestion;

import java.time.LocalDateTime;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

import org.hibernate.annotations.CreationTimestamp;

@Table(name = "raw_sensor_data")
@Entity(name = "raw_sensor_data")
public class RawSensorDataModel {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    private float soilTemp;
    private float airTemp;
    private float humidity;
    private float pressure;
    private float lux;
    private float uvVolts;
    private float tdsVolts;
    private float prongMoisture;
    private float capacitiveMoisture;

    @CreationTimestamp
    private LocalDateTime createdAt;

    public RawSensorDataModel(float soilTemp, float airTemp, float humidity, float pressure, float lux,
            float uvVolts, float tdsVolts, float prongMoisture, float capacitiveMoisture) {
        this.soilTemp = soilTemp;
        this.airTemp = airTemp;
        this.humidity = humidity;
        this.pressure = pressure;
        this.lux = lux;
        this.uvVolts = uvVolts;
        this.tdsVolts = tdsVolts;
        this.prongMoisture = prongMoisture;
        this.capacitiveMoisture = capacitiveMoisture;
    }

    public long getId() {
        return id;
    }

    public float getSoilTemp() {
        return soilTemp;
    }

    public float getAirTemp() {
        return airTemp;
    }

    public float getHumidity() {
        return humidity;
    }

    public float getPressure() {
        return pressure;
    }

    public float getLux() {
        return lux;
    }

    public float getUvVolts() {
        return uvVolts;
    }

    public float getTdsVolts() {
        return tdsVolts;
    }

    public float getProngMoisture() {
        return prongMoisture;
    }

    public float getCapacitiveMoisture() {
        return capacitiveMoisture;
    }

    public void setId(long id) {
        this.id = id;
    }

    public void setSoilTemp(float soilTemp) {
        this.soilTemp = soilTemp;
    }

    public void setAirTemp(float airTemp) {
        this.airTemp = airTemp;
    }

    public void setHumidity(float humidity) {
        this.humidity = humidity;
    }

    public void setPressure(float pressure) {
        this.pressure = pressure;
    }

    public void setLux(float lux) {
        this.lux = lux;
    }

    public void setUvVolts(float uvVolts) {
        this.uvVolts = uvVolts;
    }

    public void setTdsVolts(float tdsVolts) {
        this.tdsVolts = tdsVolts;
    }

    public void setProngMoisture(float prongMoisture) {
        this.prongMoisture = prongMoisture;
    }

    public void setCapacitiveMoisture(float capacitiveMoisture) {
        this.capacitiveMoisture = capacitiveMoisture;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }
}
