package com.aurus.server.shared;

public enum SeverityLevel {
    CRITICAL("critical"), HIGH("high"), MODERATE("moderate"), LOW("low");

    public final String value;

    SeverityLevel(String value) {
        this.value = value;
    }

    public String getValue() {
        return this.value;
    }

}
