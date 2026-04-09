package com.aurus.server.batch.derive;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Table(name = "aggregated_sensor_data")
@Entity(name = "aggregated_sensor_data")
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

    private float combinedAgronomicIndex;
    private String combinedAgronomicIndexUnit;

    private long processedSensorDataId;

    public DerivedSensorDataModel(
            DerivedSensorDataDTO combinedSoilMoistureStat, DerivedSensorDataDTO plantStressIndexStat,
            DerivedSensorDataDTO evaporationDryingRiskStat, DerivedSensorDataDTO soilFertilityIndexStat,
            DerivedSensorDataDTO heatStressIndexStat, DerivedSensorDataDTO uvStressIndexStat,
            DerivedSensorDataDTO combinedAgronomicIndexStat) {
    }

}
