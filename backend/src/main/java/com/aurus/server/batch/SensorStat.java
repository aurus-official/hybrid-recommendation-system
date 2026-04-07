package com.aurus.server.batch;

public class SensorStat {
    private float value;
    private String unit;

    public SensorStat(float value, String unit) {
        this.value = value;
        this.unit = unit;
    }

    public float getValue() {
        return value;
    }

    public String getUnit() {
        return unit;
    }

}
