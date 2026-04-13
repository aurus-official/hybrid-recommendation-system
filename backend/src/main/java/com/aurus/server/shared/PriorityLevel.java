package com.aurus.server.shared;

public enum PriorityLevel {
    P0("critical", 1f), P1("high", 0.75f), P2("moderate", 0.5f), P3("low", 0.25f);

    public final String value;
    public final float pScore;

    PriorityLevel(String value, float pScore) {
        this.value = value;
        this.pScore = pScore;
    }

    public String getValue() {
        return this.value;
    }

    public float getPScore() {
        return this.pScore;
    }

}
