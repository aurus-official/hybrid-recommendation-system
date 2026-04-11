package com.aurus.server.batch.derive.sensor;

import com.aurus.server.batch.aggregate.AggregatedSensorDataModel;

import org.jspecify.annotations.Nullable;
import org.springframework.batch.core.listener.StepExecutionListener;
import org.springframework.batch.infrastructure.item.ItemProcessor;

public class DerivedSensorDataProcessor
        implements ItemProcessor<AggregatedSensorDataModel, DerivedSensorDataModel>, StepExecutionListener {

    @Override
    public @Nullable DerivedSensorDataModel process(AggregatedSensorDataModel model) throws Exception {
        final float tdsMin = 300;
        final float tdsMax = 1500;
        final float optimalLight = 20000.0f;
        final float sigma = 8000.0f;

        float uvStressIndexValue = (model.getUvStat().value() / 11f) * 100f;
        float humidityRisk = (100f - model.getHumidityStat().value()) / 100;

        float combinedSoilMoistureValue = Math.max(0, Math.min(100, (0.6f * model.getCapacitiveMoistureStat().value())
                + (0.4f * model.getProngMoistureStat().value())));

        float plantStressIndexValue = (0.5f * combinedSoilMoistureValue)
                + (0.25f * tempStress(model.getAirTempStat().value()))
                + (0.15f * (100f - model.getHumidityStat().value()))
                + (0.10f * uvStressIndexValue);

        float evaporationDryingRiskValue = Math.max(0, Math.min(100, (0.4f * (model.getAirTempStat().value() / 35f)
                + (0.4f * humidityRisk)
                + (0.2f * (model.getUvStat().value() / 11f))) * 100));

        float soilFertilityIndexValue = Math.max(0f,
                Math.min(100f, (0.5f * (((model.getTdsStat().value() - tdsMin) / (tdsMax - tdsMin)) * 100f))
                        + (0.3f * combinedSoilMoistureValue)
                        + (0.2f * ((100f - (Math.abs(model.getSoilTempStat().value() - 22.5f) / 15) * 100f)))));

        float heatStressIndexValue = Math.max(0, Math.min(100, (0.7f * ((model.getAirTempStat().value() - 15f) / 25f))
                + (0.3f * humidityRisk)));

        float diff = model.getLuxStat().value() - optimalLight;
        float lightGrowthIndexValue = Math.max(0, Math.min(100, 100 * (-(diff * diff) / (2 * sigma * sigma))));

        float combinedAgronomicIndexValue = (0.25f * combinedSoilMoistureValue)
                + (0.25f * (100f - plantStressIndexValue))
                + (0.2f * (100f - evaporationDryingRiskValue))
                + (0.2f * soilFertilityIndexValue)
                + (0.1f * lightGrowthIndexValue);

        DerivedSensorDataDTO combinedSoilMoisture = new DerivedSensorDataDTO(combinedSoilMoistureValue, "%");
        DerivedSensorDataDTO plantStressIndex = new DerivedSensorDataDTO(plantStressIndexValue, "");
        DerivedSensorDataDTO evaporationDryingRisk = new DerivedSensorDataDTO(evaporationDryingRiskValue, "");
        DerivedSensorDataDTO soilFertilityIndex = new DerivedSensorDataDTO(soilFertilityIndexValue, "");
        DerivedSensorDataDTO heatStressIndex = new DerivedSensorDataDTO(heatStressIndexValue, "");
        DerivedSensorDataDTO uvStressIndex = new DerivedSensorDataDTO(uvStressIndexValue, "");
        DerivedSensorDataDTO lightGrowthIndex = new DerivedSensorDataDTO(lightGrowthIndexValue, "");
        DerivedSensorDataDTO combinedAgronomicIndex = new DerivedSensorDataDTO(combinedAgronomicIndexValue, "");

        DerivedSensorDataModel derivedSensorDataModel = new DerivedSensorDataModel(combinedSoilMoisture,
                plantStressIndex, evaporationDryingRisk, soilFertilityIndex, heatStressIndex, uvStressIndex,
                lightGrowthIndex,
                combinedAgronomicIndex, model.getId());

        return derivedSensorDataModel;
    }

    private float tempStress(float tempValue) {
        if (tempValue > 18f && tempValue < 30f)
            return 0f;
        return (Math.abs(tempValue - 24f) / 16f) * 100f;

    }
}
