package com.aurus.server.notification;

import java.time.LocalDateTime;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

import org.hibernate.annotations.CreationTimestamp;

@Entity(name = "notification_data_to_device")
@Table(name = "notification_data_to_device")
public class NotificationDataToDeviceModel {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    @CreationTimestamp
    private LocalDateTime createdAt;

    @CreationTimestamp
    private LocalDateTime updatedAt;

    private long notificationDeviceModelFk;

    private String ticketId;

    private long notificationDataId;

    private NotificationStatus notificationStatus;

    public NotificationDataToDeviceModel(long notificationDeviceModelFk, String ticketId, long notificationDataId) {
        this.notificationDeviceModelFk = notificationDeviceModelFk;
        this.ticketId = ticketId;
        this.notificationDataId = notificationDataId;
    }

    public NotificationDataToDeviceModel() {
    }

    public long getId() {
        return id;
    }

    public long getNotificationDeviceModelFk() {
        return notificationDeviceModelFk;
    }

    public void setNotificationDeviceModelFk(long notificationDeviceModelFk) {
        this.notificationDeviceModelFk = notificationDeviceModelFk;
    }

    public NotificationStatus getNotificationStatus() {
        return notificationStatus;
    }

    public void markNotificationStatusRead() {
        this.notificationStatus = NotificationStatus.READ;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public LocalDateTime getUpdatedAt() {
        return updatedAt;
    }

    public String getTicketId() {
        return ticketId;
    }

    public long getNotificationDataId() {
        return notificationDataId;
    }

}
