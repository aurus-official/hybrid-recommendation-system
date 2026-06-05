package com.aurus.server.notification.recommendation;

import java.time.LocalDateTime;

import com.aurus.server.shared.SeverityLevel;

public record NotificationHighPriorityRecommendationDTO(LocalDateTime createdAt, long id, SeverityLevel severityLevel) {
}
