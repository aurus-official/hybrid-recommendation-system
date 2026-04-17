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
    private int irrigationSeverityValue;

    @Lob
    private String soilNutrient;
    private int soilNutrientSeverityValue;

    @Lob
    private String microclimate;
    private int microclimateSeverityValue;

    @Lob
    private String cropOperation;
    private int cropOperationSeverityValue;

    private long derivedSensorDataId;
    private long derivedWeatherDataId;

    @CreationTimestamp
    private LocalDateTime createdAt;

    public LLMRecommendationModel(String irrigation, int irrigationSeverityValue, String soilNutrient,
            int soilNutrientSeverityValue, String microclimate, int microclimateSeverityValue, String cropOperation,
            int cropOperationSeverityValue, long derivedSensorDataId, long derivedWeatherDataId) {
        this.irrigation = irrigation;
        this.irrigationSeverityValue = irrigationSeverityValue;
        this.soilNutrient = soilNutrient;
        this.soilNutrientSeverityValue = soilNutrientSeverityValue;
        this.microclimate = microclimate;
        this.microclimateSeverityValue = microclimateSeverityValue;
        this.cropOperation = cropOperation;
        this.cropOperationSeverityValue = cropOperationSeverityValue;
        this.derivedSensorDataId = derivedSensorDataId;
        this.derivedWeatherDataId = derivedWeatherDataId;
    }

    public LLMRecommendationModel() {
    }

    public long getId() {
        return id;
    }

    public String getIrrigation() {
        return irrigation;
    }

    public int getIrrigationSeverityValue() {
        return irrigationSeverityValue;
    }

    public String getSoilNutrient() {
        return soilNutrient;
    }

    public int getSoilNutrientSeverityValue() {
        return soilNutrientSeverityValue;
    }

    public String getMicroclimate() {
        return microclimate;
    }

    public int getMicroclimateSeverityValue() {
        return microclimateSeverityValue;
    }

    public String getCropOperation() {
        return cropOperation;
    }

    public int getCropOperationSeverityValue() {
        return cropOperationSeverityValue;
    }

    public long getDerivedSensorDataId() {
        return derivedSensorDataId;
    }

    public long getDerivedWeatherDataId() {
        return derivedWeatherDataId;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

}
