package com.aurus.server.reading_status;

import java.time.LocalDateTime;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

import com.fasterxml.jackson.annotation.JsonProperty;

import org.hibernate.annotations.CreationTimestamp;

@Table(name = "reading_status")
@Entity(name = "reading_status")
public class ReadingStatusModel {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    private boolean isValidSoilTemp;
    private boolean isValidAirTemp;
    private boolean isValidHumidity;
    private boolean isValidPressure;
    private boolean isValidLux;
    private boolean isValidUvVolts;
    private boolean isValidTdsVolts;
    private boolean isValidProngMoisture;
    private boolean isValidCapacitiveMoisture;

    @CreationTimestamp
    private LocalDateTime createdAt;

    public ReadingStatusModel() {
    }

    public ReadingStatusModel(boolean isValidSoilTemp, boolean isValidAirTemp, boolean isValidHumidity,
            boolean isValidPressure, boolean isValidLux, boolean isValidUvVolts, boolean isValidTdsVolts,
            boolean isValidProngMoisture, boolean isValidCapacitiveMoisture) {
        this.isValidSoilTemp = isValidSoilTemp;
        this.isValidAirTemp = isValidAirTemp;
        this.isValidHumidity = isValidHumidity;
        this.isValidPressure = isValidPressure;
        this.isValidLux = isValidLux;
        this.isValidUvVolts = isValidUvVolts;
        this.isValidTdsVolts = isValidTdsVolts;
        this.isValidProngMoisture = isValidProngMoisture;
        this.isValidCapacitiveMoisture = isValidCapacitiveMoisture;
    }

    public long getId() {
        return id;
    }

    @JsonProperty("isValidAirTemp")
    public boolean isValidAirTemp() {
        return isValidAirTemp;
    }

    @JsonProperty("isValidCapacitiveMoisture")
    public boolean isValidCapacitiveMoisture() {
        return isValidCapacitiveMoisture;
    }

    @JsonProperty("isValidHumidity")
    public boolean isValidHumidity() {
        return isValidHumidity;
    }

    @JsonProperty("isValidLux")
    public boolean isValidLux() {
        return isValidLux;
    }

    @JsonProperty("isValidPressure")
    public boolean isValidPressure() {
        return isValidPressure;
    }

    @JsonProperty("isValidProngMoisture")
    public boolean isValidProngMoisture() {
        return isValidProngMoisture;
    }

    @JsonProperty("isValidSoilTemp")
    public boolean isValidSoilTemp() {
        return isValidSoilTemp;
    }

    @JsonProperty("isValidTdsVolts")
    public boolean isValidTdsVolts() {
        return isValidTdsVolts;
    }

    @JsonProperty("isValidUvVolts")
    public boolean isValidUvVolts() {
        return isValidUvVolts;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    @Override
    public String toString() {
        return "ReadingStatusModel{id=" + id + ", isValidSoilTemp=" + isValidSoilTemp + ", isValidAirTemp="
                + isValidAirTemp + ", isValidHumidity=" + isValidHumidity + ", isValidPressure=" + isValidPressure
                + ", isValidLux=" + isValidLux + ", isValidUvVolts=" + isValidUvVolts + ", isValidTdsVolts="
                + isValidTdsVolts + ", isValidProngMoisture=" + isValidProngMoisture + ", isValidCapacitiveMoisture="
                + isValidCapacitiveMoisture + ", createdAt=" + createdAt + "}";
    }

}
