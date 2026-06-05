package com.aurus.server.engine;

public class EngineRecommendationEvidence {

    private String parameter;

    private float currentValue;

    private float thresholdValue;

    private String comparisonOperator;

    public String getParameter() {
        return parameter;
    }

    public void setParameter(String parameter) {
        this.parameter = parameter;
    }

    public float getCurrentValue() {
        return currentValue;
    }

    public void setCurrentValue(float currentValue) {
        this.currentValue = currentValue;
    }

    public float getThresholdValue() {
        return thresholdValue;
    }

    public void setThresholdValue(float thresholdValue) {
        this.thresholdValue = thresholdValue;
    }

    public String getComparisonOperator() {
        return comparisonOperator;
    }

    public void setComparisonOperator(String comparisonOperator) {
        this.comparisonOperator = comparisonOperator;
    }

    @Override
    public String toString() {
        return "RecommendationEvidence{" +
                "parameter='" + parameter + '\'' +
                ", currentValue=" + currentValue +
                ", thresholdValue=" + thresholdValue +
                ", comparisonOperator='" + comparisonOperator + '\'' +
                '}';
    }
}
