package com.aurus.server.shared;

public enum SeverityLevel {
    CRITICAL("critical", 0), HIGH("high", 1), MODERATE("moderate", 2), LOW("low", 3);

    public final String value;
    public final int num;

    SeverityLevel(String value, int num) {
        this.value = value;
        this.num = num;
    }

    public String getValue() {
        return this.value;
    }

    public int getNum() {
        return this.num;
    }

}
