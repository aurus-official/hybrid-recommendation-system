package com.aurus.server.ingestion;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Table(name = "raw_sensor_data")
@Entity(name = "raw_sensor_data")
public class RawSensorDataModel {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    private float waterTemp;
    private float airTemp;
    private float humidity;
    private float pressure;
    private float lux;
    private float uvVolts;
    private float tdsVolts;
    private float prongMoisture;
    private float capacitiveMoisture;

    public RawSensorDataModel(float waterTemp, float airTemp, float humidity, float pressure, float lux,
            float uvVolts, float tdsVolts, float prongMoisture, float capacitiveMoisture) {
        this.waterTemp = waterTemp;
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

    public float getWaterTemp() {
        return waterTemp;
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
}
