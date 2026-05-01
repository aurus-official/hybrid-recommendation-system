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
public class NotificationDataModel {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    @CreationTimestamp
    private LocalDateTime createdAt;

    private long llmRecommendationId;
    private LocalDateTime llmRecommendationCreatedAt;

    public NotificationDataModel(long llmRecommendationId, LocalDateTime llmRecommendationCreatedAt) {
        this.llmRecommendationId = llmRecommendationId;
        this.llmRecommendationCreatedAt = llmRecommendationCreatedAt;
    }

    public NotificationDataModel() {
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

    public long getLlmRecommendationId() {
        return llmRecommendationId;
    }

    public LocalDateTime getLlmRecommendationCreatedAt() {
        return llmRecommendationCreatedAt;
    }

}
