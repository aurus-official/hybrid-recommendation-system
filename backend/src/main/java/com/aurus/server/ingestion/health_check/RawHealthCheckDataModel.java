package com.aurus.server.ingestion.health_check;

import java.time.LocalDateTime;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

import org.hibernate.annotations.CreationTimestamp;

@Table(name = "raw_health_check_data")
@Entity(name = "raw_health_check_data")
public class RawHealthCheckDataModel {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    private boolean ads1;
    private boolean ads2;
    private boolean bme280;
    private boolean guvas12sd;
    private boolean ds18b20;

    @CreationTimestamp
    private LocalDateTime createdAt;

    public RawHealthCheckDataModel() {
    }

    public RawHealthCheckDataModel(boolean ads1, boolean ads2, boolean bme280, boolean guvas12sd, boolean ds18b20) {
        this.ads1 = ads1;
        this.ads2 = ads2;
        this.bme280 = bme280;
        this.guvas12sd = guvas12sd;
        this.ds18b20 = ds18b20;
    }

    public long getId() {
        return id;
    }

    public boolean isAds1() {
        return ads1;
    }

    public boolean isAds2() {
        return ads2;
    }

    public boolean isBme280() {
        return bme280;
    }

    public boolean isGuvas12sd() {
        return guvas12sd;
    }

    public boolean isDs18b20() {
        return ds18b20;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }
}
