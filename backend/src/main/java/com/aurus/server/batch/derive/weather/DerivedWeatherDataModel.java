package com.aurus.server.batch.derive.weather;

import java.io.Serializable;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Table(name = "derived_weather_data")
@Entity(name = "derived_weather_data")
public class DerivedWeatherDataModel implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    private float plantStressIndex;
    private String plantStressIndexUnit;

    private float heatStressIndex;
    private String heatStressIndexUnit;

    private float rainImpactIndex;
    private String rainImpactIndexUnit;

    private float waterBalanceIndex;
    private String waterBalanceIndexUnit;

    private long aggregatedWeatherDataId;

    public DerivedWeatherDataModel() {
    }

    public DerivedWeatherDataModel(
            DerivedWeatherDataDTO plantStressIndex,
            DerivedWeatherDataDTO heatStressIndex, DerivedWeatherDataDTO rainImpactIndex,
            DerivedWeatherDataDTO waterBalanceIndex, long aggregatedWeatherDataId) {

        this.plantStressIndex = plantStressIndex.value();
        this.plantStressIndexUnit = plantStressIndex.unit();

        this.heatStressIndex = heatStressIndex.value();
        this.heatStressIndexUnit = heatStressIndex.unit();

        this.rainImpactIndex = rainImpactIndex.value();
        this.rainImpactIndexUnit = rainImpactIndex.unit();

        this.waterBalanceIndex = waterBalanceIndex.value();
        this.waterBalanceIndexUnit = waterBalanceIndex.unit();

        this.aggregatedWeatherDataId = aggregatedWeatherDataId;

    }

    public long getId() {
        return id;
    }

    public float getPlantStressIndex() {
        return plantStressIndex;
    }

    public String getPlantStressIndexUnit() {
        return plantStressIndexUnit;
    }

    public float getRainImpactIndex() {
        return rainImpactIndex;
    }

    public String getRainImpactIndexUnit() {
        return rainImpactIndexUnit;
    }

    public float getWaterBalanceIndex() {
        return waterBalanceIndex;
    }

    public String getWaterBalanceIndexUnit() {
        return waterBalanceIndexUnit;
    }

    public long getAggregatedWeatherDataId() {
        return aggregatedWeatherDataId;
    }

    public float getHeatStressIndex() {
        return heatStressIndex;
    }

    public String getHeatStressIndexUnit() {
        return heatStressIndexUnit;
    }

}
