package com.aurus.server.batch.derive.weather;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Table(name = "derived_weather_data")
@Entity(name = "derived_weather_data")
public class DerivedWeatherDataModel {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    private float plantStressIndex;
    private String plantStressIndexUnit;

    private float evaporationDryingRisk;
    private String evaporationDryingRiskUnit;

    private float rainImpactIndex;
    private String rainImpactIndexUnit;

    private float waterBalanceIndex;
    private String waterBalanceIndexUnit;

    private long aggregatedWeatherDataId;

    public DerivedWeatherDataModel() {
    }

    public DerivedWeatherDataModel(
            DerivedWeatherDataDTO plantStressIndexStat,
            DerivedWeatherDataDTO evaporationDryingRiskStat, DerivedWeatherDataDTO rainImpactIndexStat,
            DerivedWeatherDataDTO waterBalanceIndexStat, long aggregatedWeatherDataId) {

        this.plantStressIndex = plantStressIndexStat.value();
        this.plantStressIndexUnit = plantStressIndexStat.unit();

        this.evaporationDryingRisk = evaporationDryingRiskStat.value();
        this.evaporationDryingRiskUnit = evaporationDryingRiskStat.unit();

        this.rainImpactIndex = rainImpactIndexStat.value();
        this.rainImpactIndexUnit = rainImpactIndexStat.unit();

        this.waterBalanceIndex = waterBalanceIndexStat.value();
        this.waterBalanceIndexUnit = waterBalanceIndexStat.unit();

        this.aggregatedWeatherDataId = aggregatedWeatherDataId;

    }

    public DerivedWeatherDataDTO getPlantStressIndex() {
        return new DerivedWeatherDataDTO(plantStressIndex, plantStressIndexUnit);
    }

    public DerivedWeatherDataDTO getEvaporationDryingRisk() {
        return new DerivedWeatherDataDTO(evaporationDryingRisk, evaporationDryingRiskUnit);
    }

    public DerivedWeatherDataDTO getRainImpactIndex() {
        return new DerivedWeatherDataDTO(rainImpactIndex, rainImpactIndexUnit);
    }

    public DerivedWeatherDataDTO getWaterBalanceIndex() {
        return new DerivedWeatherDataDTO(waterBalanceIndex, waterBalanceIndexUnit);
    }

    public long getAggregatedWeatherDataId() {
        return aggregatedWeatherDataId;
    }

}
