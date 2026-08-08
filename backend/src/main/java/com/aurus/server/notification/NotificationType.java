package com.aurus.server.notification;

public enum NotificationType {
    RECOMMENDATION_SEVERITY_ISSUE("recommendation_severity_issue"), READING_STATUS_ISSUE("reading_status_issue"),
    HARDWARE_STATUS_ISSUE("hardware_status_issue");

    String name;

    NotificationType(String name) {
        this.name = name;
    }

    public String getName() {
        return this.name;
    }
}
