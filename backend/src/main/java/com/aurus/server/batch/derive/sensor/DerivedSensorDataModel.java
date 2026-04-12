package com.aurus.server.batch.derive.sensor;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Table(name = "derived_sensor_data")
@Entity(name = "derived_sensor_data")
public class DerivedSensorDataModel {

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

    private long processedSensorDataId;

    public DerivedSensorDataModel() {
    }

    public DerivedSensorDataModel(
            DerivedSensorDataDTO combinedSoilMoisture, DerivedSensorDataDTO plantStressIndex,
            DerivedSensorDataDTO evaporationDryingRisk, DerivedSensorDataDTO soilFertilityIndex,
            DerivedSensorDataDTO heatStressIndex, DerivedSensorDataDTO uvStressIndex,
            DerivedSensorDataDTO lightGrowthIndex, DerivedSensorDataDTO combinedAgronomicIndex,
            long processedSensorDataId) {

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

        this.processedSensorDataId = processedSensorDataId;
    }

    public DerivedSensorDataDTO getCombinedSoilMoisture() {
        return new DerivedSensorDataDTO(combinedSoilMoisture, combinedSoilMoistureUnit);
    }

    public DerivedSensorDataDTO getPlantStressIndex() {
        return new DerivedSensorDataDTO(plantStressIndex, plantStressIndexUnit);
    }

    public DerivedSensorDataDTO getEvaporationDryingRisk() {
        return new DerivedSensorDataDTO(evaporationDryingRisk, evaporationDryingRiskUnit);
    }

    public DerivedSensorDataDTO getSoilFertilityIndex() {
        return new DerivedSensorDataDTO(soilFertilityIndex, soilFertilityIndexUnit);
    }

    public DerivedSensorDataDTO getHeatStressIndex() {
        return new DerivedSensorDataDTO(heatStressIndex, heatStressIndexUnit);
    }

    public DerivedSensorDataDTO getUvStressIndex() {
        return new DerivedSensorDataDTO(uvStressIndex, uvStressIndexUnit);
    }

    public DerivedSensorDataDTO getLightGrowthIndex() {
        return new DerivedSensorDataDTO(lightGrowthIndex, lightGrowthIndexUnit);
    }

    public DerivedSensorDataDTO getCombinedAgronomicIndex() {
        return new DerivedSensorDataDTO(combinedAgronomicIndex, combinedAgronomicIndexUnit);
    }

    public long getId() {
        return id;
    }

    public long getProcessedSensorDataId() {
        return processedSensorDataId;
    }

}
