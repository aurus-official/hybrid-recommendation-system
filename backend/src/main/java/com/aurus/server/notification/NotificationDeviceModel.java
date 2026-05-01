package com.aurus.server.notification;

import java.time.LocalDateTime;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

import org.hibernate.annotations.UpdateTimestamp;

@Entity(name = "notification_device_data")
@Table(name = "notification_device_data")
public class NotificationDeviceModel {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;
    private String deviceId;
    private String expoPushToken;

    @UpdateTimestamp
    private LocalDateTime updatedAt;

    public NotificationDeviceModel() {
    }

    public NotificationDeviceModel(String deviceId, String expoPushToken) {
        this.deviceId = deviceId;
        this.expoPushToken = expoPushToken;
    }

    public long getId() {
        return id;
    }

    public String getDeviceId() {
        return deviceId;
    }

    public LocalDateTime getUpdatedAt() {
        return updatedAt;
    }

    public String getExpoPushToken() {
        return expoPushToken;
    }

    public void setExpoPushToken(String expoPushToken) {
        this.expoPushToken = expoPushToken;
    }
}
