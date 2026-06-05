package com.aurus.server.engine;

import java.util.ArrayList;
import java.util.List;

import com.aurus.server.shared.CategoryType;
import com.aurus.server.shared.PriorityLevel;
import com.aurus.server.shared.SeverityLevel;

public class EngineCategoryOutputDTO {

    private CategoryType categoryType;

    private PriorityLevel priorityLevel;

    private SeverityLevel severityLevel;

    private String action;

    private float confidence;

    private String rationale;

    private List<EngineRecommendationEvidence> evidences = new ArrayList<>();

    public CategoryType getCategoryType() {
        return categoryType;
    }

    public void setCategoryType(CategoryType categoryType) {
        this.categoryType = categoryType;
    }

    public PriorityLevel getPriorityLevel() {
        return priorityLevel;
    }

    public void setPriorityLevel(PriorityLevel priorityLevel) {
        this.priorityLevel = priorityLevel;
    }

    public SeverityLevel getSeverityLevel() {
        return severityLevel;
    }

    public void setSeverityLevel(SeverityLevel severityLevel) {
        this.severityLevel = severityLevel;
    }

    public String getAction() {
        return action;
    }

    public void setAction(String action) {
        this.action = action;
    }

    public float getConfidence() {
        return confidence;
    }

    public void setConfidence(float confidence) {
        this.confidence = confidence;
    }

    public String getRationale() {
        return rationale;
    }

    public void setRationale(String rationale) {
        this.rationale = rationale;
    }

    public List<EngineRecommendationEvidence> getEvidences() {
        return evidences;
    }

    public void setEvidences(List<EngineRecommendationEvidence> evidences) {
        this.evidences = evidences;
    }

    public void addEvidence(
            String parameter,
            float currentValue,
            float thresholdValue,
            String comparisonOperator) {
        EngineRecommendationEvidence evidence = new EngineRecommendationEvidence();

        evidence.setParameter(parameter);
        evidence.setCurrentValue(currentValue);
        evidence.setThresholdValue(thresholdValue);
        evidence.setComparisonOperator(comparisonOperator);

        evidences.add(evidence);
    }

    public float getScore() {
        return Math.round(
                (0.6f * priorityLevel.getPScore()) +
                        (0.4f * confidence)
                                * 10_000.0f)
                / 10_000.0f;
    }

    @Override
    public String toString() {
        return "EngineCategoryOutputDTO{" +
                "categoryType=" + categoryType +
                ", priorityLevel=" + priorityLevel +
                ", severityLevel=" + severityLevel +
                ", action='" + action + '\'' +
                ", confidence=" + confidence +
                ", rationale='" + rationale + '\'' +
                ", evidences=" + evidences +
                ", score=" + getScore() +
                '}';
    }
}
