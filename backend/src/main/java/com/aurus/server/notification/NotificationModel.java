package com.aurus.server.notification;

import java.time.LocalDateTime;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

import org.hibernate.annotations.CreationTimestamp;

@Entity(name = "notification_data")
@Table(name = "notification_data")
public class NotificationModel {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    @CreationTimestamp
    private LocalDateTime createdAt;

    private long referenceId;
    private LocalDateTime referenceCreatedAt;

    private NotificationType notificationType;

    public NotificationModel() {
    }

    public NotificationModel(long referenceId, LocalDateTime referenceCreatedAt, NotificationType notificationType) {
        this.referenceId = referenceId;
        this.referenceCreatedAt = referenceCreatedAt;
        this.notificationType = notificationType;
    }

    public long getId() {
        return id;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public long getReferenceId() {
        return referenceId;
    }

    public LocalDateTime getReferenceCreatedAt() {
        return referenceCreatedAt;
    }

    public NotificationType getNotificationType() {
        return notificationType;
    }
}
