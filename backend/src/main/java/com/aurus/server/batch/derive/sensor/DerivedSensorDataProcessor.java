package com.aurus.server.batch.derive.sensor;

import com.aurus.server.batch.aggregate.sensor.AggregatedSensorDataModel;
import com.aurus.server.shared.TdsWindowNormalization;

import org.jspecify.annotations.Nullable;
import org.springframework.batch.core.listener.StepExecutionListener;
import org.springframework.batch.infrastructure.item.ItemProcessor;

public class DerivedSensorDataProcessor
        implements ItemProcessor<AggregatedSensorDataModel, DerivedSensorDataModel>, StepExecutionListener {

    private final TdsWindowNormalization tdsWindowNormalization;

    public DerivedSensorDataProcessor(TdsWindowNormalization tdsWindowNormalization) {
        this.tdsWindowNormalization = tdsWindowNormalization;
    }

    @Override
    public @Nullable DerivedSensorDataModel process(AggregatedSensorDataModel model) throws Exception {
        final float tdsMin = tdsWindowNormalization.getMinValue();
        final float tdsMax = tdsWindowNormalization.getMaxValue();
        final float range = tdsMax - tdsMin;
        final float tdsNorm = Math.max(0f,
                Math.min(1f, (range == 0f) ? 0f : (model.getTds().value() - tdsMin) / range));
        final float optimalLight = 20000.0f;
        final float sigma = 8000.0f;

        float uvStressIndexValue = model.getUv().value() / 11f;
        float humidityStress = (100f - model.getHumidity().value()) / 100f;

        float combinedSoilMoistureValue = Math.max(0f, Math.min(1f, (0.6f * model.getCapacitiveMoisture().value())
                + (0.4f * model.getProngMoisture().value()) / 100f));

        float plantStressIndexValue = (0.5f * combinedSoilMoistureValue)
                + (0.25f * tempStress(model.getAirTemp().value()))
                + (0.15f * humidityStress)
                + (0.10f * uvStressIndexValue);

        float evaporationDryingRiskValue = Math.max(0f, Math.min(1f, (0.4f * tempStress(model.getAirTemp().value())
                + (0.4f * humidityStress)
                + (0.2f * (model.getUv().value() / 11f)))));

        float soilFertilityIndexValue = Math.max(0f,
                Math.min(1f, (0.5f * tdsNorm)
                        + (0.3f * combinedSoilMoistureValue)
                        + (0.2f * Math.max(0f, Math.min(1f,
                                1f - (Math.abs(model.getSoilTemp().value() - 22.5f) / 15f))))));

        float heatStressIndexValue = Math.max(0f,
                Math.min(1f, (0.7f * tempStress(model.getAirTemp().value())) + (0.3f * humidityStress)));

        float diff = model.getLux().value() - optimalLight;
        float lightGrowthIndexValue = Math.max(0f,
                Math.min(1f, (float) Math.exp(-(diff * diff) / (2 * sigma * sigma))));

        float combinedAgronomicIndexValue = (0.25f * combinedSoilMoistureValue)
                + (0.25f * (1f - plantStressIndexValue))
                + (0.2f * (1f - evaporationDryingRiskValue))
                + (0.2f * soilFertilityIndexValue)
                + (0.1f * lightGrowthIndexValue);

        DerivedSensorDataDTO combinedSoilMoisture = new DerivedSensorDataDTO(
                toFourDigitsDecimal(combinedSoilMoistureValue), "normalized");
        DerivedSensorDataDTO plantStressIndex = new DerivedSensorDataDTO(toFourDigitsDecimal(plantStressIndexValue),
                "normalized");
        DerivedSensorDataDTO evaporationDryingRisk = new DerivedSensorDataDTO(
                toFourDigitsDecimal(evaporationDryingRiskValue), "normalized");
        DerivedSensorDataDTO soilFertilityIndex = new DerivedSensorDataDTO(toFourDigitsDecimal(soilFertilityIndexValue),
                "normalized");
        DerivedSensorDataDTO heatStressIndex = new DerivedSensorDataDTO(toFourDigitsDecimal(heatStressIndexValue),
                "normalized");
        DerivedSensorDataDTO uvStressIndex = new DerivedSensorDataDTO(toFourDigitsDecimal(uvStressIndexValue),
                "normalized");
        DerivedSensorDataDTO lightGrowthIndex = new DerivedSensorDataDTO(toFourDigitsDecimal(lightGrowthIndexValue),
                "normalized");
        DerivedSensorDataDTO combinedAgronomicIndex = new DerivedSensorDataDTO(
                toFourDigitsDecimal(combinedAgronomicIndexValue),
                "normalized");

        DerivedSensorDataModel derivedSensorDataModel = new DerivedSensorDataModel(combinedSoilMoisture,
                plantStressIndex, evaporationDryingRisk, soilFertilityIndex, heatStressIndex, uvStressIndex,
                lightGrowthIndex,
                combinedAgronomicIndex, model.getId());

        return derivedSensorDataModel;
    }

    private float toFourDigitsDecimal(float value) {
        return Math.round(value * 10_000.0f) / 10_000.0f;
    }

    private float tempStress(float tempValue) {
        return 1f - (float) Math.exp(-Math.pow((tempValue - 24f), 2) / 50f);
    }
}
