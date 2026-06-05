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

        float uvStressIndexValue = clamp(model.getUv().value() / 11f);

        float humidityNormalized = clamp(model.getHumidity().value() / 100f);

        float humidityStressValue = humidityStress(model.getHumidity().value());

        float tempStressValue = tempStress(model.getAirTemp().value());

        float tdsNorm = calculateTdsNorm(model.getTds().value());

        float combinedSoilMoistureValue = clamp(
                (0.65f * model.getCapacitiveMoisture().value() / 100f) +
                        (0.35f * model.getProngMoisture().value()) / 100f);

        System.out.println("COMBINE SOIL MOISTURE VALUE : " + combinedSoilMoistureValue);

        float plantStressIndexValue = clamp(
                (0.45f * (1f - combinedSoilMoistureValue)) +
                        (0.30f * tempStressValue) +
                        (0.15f * humidityStressValue) +
                        (0.10f * uvStressIndexValue));

        float evaporationDryingRiskValue = clamp(
                (0.55f * tempStressValue) +
                        (0.35f * (1f - humidityNormalized)) +
                        (0.20f * uvStressIndexValue));

        float soilTempSuitability = clamp(
                1f - (Math.abs(model.getSoilTemp().value() - 24f)
                        / 12f));

        float soilFertilityIndexValue = clamp(
                (0.50f * tdsNorm) +
                        (0.30f * combinedSoilMoistureValue) +
                        (0.20f * soilTempSuitability));

        float heatStressIndexValue = clamp(
                (0.75f * tempStressValue) +
                        (0.15f * humidityStressValue) +
                        (0.10f * uvStressIndexValue));

        final float optimalLight = 18000f;
        final float sigma = 6000f;

        float diff = model.getLux().value() - optimalLight;

        float lightGrowthIndexValue = clamp(
                (float) Math.exp(
                        -(diff * diff) /
                                (2 * sigma * sigma)));

        float combinedAgronomicIndexValue = clamp(
                (0.30f * combinedSoilMoistureValue) +
                        (0.25f * soilFertilityIndexValue) +
                        (0.20f * lightGrowthIndexValue) +
                        (0.15f * (1f - heatStressIndexValue)) +
                        (0.10f * (1f - plantStressIndexValue)));

        DerivedSensorDataDTO combinedSoilMoisture = new DerivedSensorDataDTO(
                toFourDigitsDecimal(combinedSoilMoistureValue),
                "normalized");

        DerivedSensorDataDTO plantStressIndex = new DerivedSensorDataDTO(
                toFourDigitsDecimal(plantStressIndexValue),
                "normalized");

        DerivedSensorDataDTO evaporationDryingRisk = new DerivedSensorDataDTO(
                toFourDigitsDecimal(evaporationDryingRiskValue),
                "normalized");

        DerivedSensorDataDTO soilFertilityIndex = new DerivedSensorDataDTO(
                toFourDigitsDecimal(soilFertilityIndexValue),
                "normalized");

        DerivedSensorDataDTO heatStressIndex = new DerivedSensorDataDTO(
                toFourDigitsDecimal(heatStressIndexValue),
                "normalized");

        DerivedSensorDataDTO uvStressIndex = new DerivedSensorDataDTO(
                toFourDigitsDecimal(uvStressIndexValue),
                "normalized");

        DerivedSensorDataDTO lightGrowthIndex = new DerivedSensorDataDTO(
                toFourDigitsDecimal(lightGrowthIndexValue),
                "normalized");

        DerivedSensorDataDTO combinedAgronomicIndex = new DerivedSensorDataDTO(
                toFourDigitsDecimal(combinedAgronomicIndexValue),
                "normalized");

        return new DerivedSensorDataModel(
                combinedSoilMoisture,
                plantStressIndex,
                evaporationDryingRisk,
                soilFertilityIndex,
                heatStressIndex,
                uvStressIndex,
                lightGrowthIndex,
                combinedAgronomicIndex,
                model.getId());
    }

    private float clamp(float value) {
        return Math.max(0f, Math.min(1f, value));
    }

    private float toFourDigitsDecimal(float value) {
        return Math.round(value * 10_000.0f) / 10_000.0f;
    }

    private float normalizeFixedRange(float value, float min, float max) {
        return clamp((value - min) / (max - min));
    }

    private float tempStress(float temp) {

        if (temp <= 26f)
            return 0f;

        if (temp >= 40f)
            return 1f;

        return (temp - 26f) / (40f - 26f);
    }

    private float humidityStress(float humidity) {

        float optimal = 75f;

        return clamp(
                Math.abs(humidity - optimal) / 35f);
    }

    private float calculateTdsNorm(float tdsValue) {

        tdsWindowNormalization.addTdsToWindow(tdsValue);

        if (tdsWindowNormalization.getSize() < 5) {
            return normalizeFixedRange(tdsValue, 300f, 1200f);
        }

        float dynamicMin = tdsWindowNormalization.getMinValue();
        float dynamicMax = tdsWindowNormalization.getMaxValue();

        if ((dynamicMax - dynamicMin) < 50f) {
            return normalizeFixedRange(tdsValue, 300f, 1200f);
        }

        float dynamicNorm = (tdsValue - dynamicMin) /
                (dynamicMax - dynamicMin);

        dynamicNorm = clamp(dynamicNorm);

        float fixedNorm = normalizeFixedRange(tdsValue, 300f, 1200f);

        return clamp(
                (0.6f * fixedNorm) +
                        (0.4f * dynamicNorm));
    }

}
