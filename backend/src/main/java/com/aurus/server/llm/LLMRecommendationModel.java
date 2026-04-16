package com.aurus.server.llm;

import java.time.LocalDateTime;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Lob;
import jakarta.persistence.Table;

import org.hibernate.annotations.CreationTimestamp;

@Entity(name = "llm_recommendation_data")
@Table(name = "llm_recommendation_data")
public class LLMRecommendationModel {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    @Lob
    private String irrigation;

    @Lob
    private String soilNutrient;

    @Lob
    private String microclimate;

    @Lob
    private String cropOperation;

    private long derivedSensorDataId;
    private long derivedWeatherDataId;

    @CreationTimestamp
    private LocalDateTime createdAt;

    public LLMRecommendationModel(String irrigation, String soilNutrient, String microclimate, String cropOperation,
            long derivedSensorDataId, long derivedWeatherDataId) {
        this.irrigation = irrigation;
        this.soilNutrient = soilNutrient;
        this.microclimate = microclimate;
        this.cropOperation = cropOperation;
        this.derivedSensorDataId = derivedSensorDataId;
        this.derivedWeatherDataId = derivedWeatherDataId;
    }

    public long getId() {
        return id;
    }

    public String getIrrigation() {
        return irrigation;
    }

    public String getSoilNutrient() {
        return soilNutrient;
    }

    public String getMicroclimate() {
        return microclimate;
    }

    public String getCropOperation() {
        return cropOperation;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public long getDerivedSensorDataId() {
        return derivedSensorDataId;
    }

    public long getDerivedWeatherDataId() {
        return derivedWeatherDataId;
    }

}
