package com.aurus.server.engine;

import com.aurus.server.shared.CategoryType;
import com.aurus.server.shared.PriorityLevel;
import com.aurus.server.shared.SeverityLevel;

public class EngineCategoryOutputDTO {
    private CategoryType categoryType;
    private PriorityLevel priorityLevel;
    private SeverityLevel severityLevel;
    private String action;
    private float confidence;
    private float score = -1f;

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

    public float getScore() {
        if (score == -1) {
            this.score = (0.6f * priorityLevel.getPScore()) + (0.4f * confidence);
        }
        return this.score;
    }
}
