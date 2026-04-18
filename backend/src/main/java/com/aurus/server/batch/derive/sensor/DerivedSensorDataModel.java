package com.aurus.server.batch.derive.sensor;

import java.io.Serializable;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Table(name = "derived_sensor_data")
@Entity(name = "derived_sensor_data")
public class DerivedSensorDataModel implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    private float combinedSoilMoisture;
    private String combinedSoilMoistureUnit;

    private float plantStressIndex;
    private String plantStressIndexUnit;

    private float evaporationDryingRisk;
    private String evaporationDryingRiskUnit;

    private float soilFertilityIndex;
    private String soilFertilityIndexUnit;

    private float heatStressIndex;
    private String heatStressIndexUnit;

    private float uvStressIndex;
    private String uvStressIndexUnit;

    private float lightGrowthIndex;
    private String lightGrowthIndexUnit;

    private float combinedAgronomicIndex;
    private String combinedAgronomicIndexUnit;

    private long aggregatedSensorDataId;

    public DerivedSensorDataModel() {
    }

    public DerivedSensorDataModel(
            DerivedSensorDataDTO combinedSoilMoisture, DerivedSensorDataDTO plantStressIndex,
            DerivedSensorDataDTO evaporationDryingRisk, DerivedSensorDataDTO soilFertilityIndex,
            DerivedSensorDataDTO heatStressIndex, DerivedSensorDataDTO uvStressIndex,
            DerivedSensorDataDTO lightGrowthIndex, DerivedSensorDataDTO combinedAgronomicIndex,
            long aggregatedSensorDataId) {

        this.combinedSoilMoisture = combinedSoilMoisture.value();
        this.combinedSoilMoistureUnit = combinedSoilMoisture.unit();

        this.plantStressIndex = plantStressIndex.value();
        this.plantStressIndexUnit = plantStressIndex.unit();

        this.evaporationDryingRisk = evaporationDryingRisk.value();
        this.evaporationDryingRiskUnit = evaporationDryingRisk.unit();

        this.soilFertilityIndex = soilFertilityIndex.value();
        this.soilFertilityIndexUnit = soilFertilityIndex.unit();

        this.heatStressIndex = heatStressIndex.value();
        this.heatStressIndexUnit = heatStressIndex.unit();

        this.uvStressIndex = uvStressIndex.value();
        this.uvStressIndexUnit = uvStressIndex.unit();

        this.combinedAgronomicIndex = combinedAgronomicIndex.value();
        this.combinedAgronomicIndexUnit = combinedAgronomicIndex.unit();

        this.lightGrowthIndex = lightGrowthIndex.value();
        this.lightGrowthIndexUnit = lightGrowthIndex.unit();

        this.aggregatedSensorDataId = aggregatedSensorDataId;
    }

    public long getId() {
        return id;
    }

    public float getCombinedSoilMoisture() {
        return combinedSoilMoisture;
    }

    public String getCombinedSoilMoistureUnit() {
        return combinedSoilMoistureUnit;
    }

    public float getPlantStressIndex() {
        return plantStressIndex;
    }

    public String getPlantStressIndexUnit() {
        return plantStressIndexUnit;
    }

    public float getEvaporationDryingRisk() {
        return evaporationDryingRisk;
    }

    public String getEvaporationDryingRiskUnit() {
        return evaporationDryingRiskUnit;
    }

    public float getSoilFertilityIndex() {
        return soilFertilityIndex;
    }

    public String getSoilFertilityIndexUnit() {
        return soilFertilityIndexUnit;
    }

    public float getHeatStressIndex() {
        return heatStressIndex;
    }

    public String getHeatStressIndexUnit() {
        return heatStressIndexUnit;
    }

    public float getUvStressIndex() {
        return uvStressIndex;
    }

    public String getUvStressIndexUnit() {
        return uvStressIndexUnit;
    }

    public float getLightGrowthIndex() {
        return lightGrowthIndex;
    }

    public String getLightGrowthIndexUnit() {
        return lightGrowthIndexUnit;
    }

    public float getCombinedAgronomicIndex() {
        return combinedAgronomicIndex;
    }

    public String getCombinedAgronomicIndexUnit() {
        return combinedAgronomicIndexUnit;
    }

    public long getAggregatedSensorDataId() {
        return aggregatedSensorDataId;
    }

}
