package com.aurus.server.notification;

public enum NotificationType {
    RECOMMENDATION_SEVERITY_ISSUE("recommendation_severity_issue"), SYSTEM_HEALTH_ISSUE("system_health_issue");

    String name;

    NotificationType(String name) {
        this.name = name;
    }

    public String getName() {
        return this.name;
    }
}
