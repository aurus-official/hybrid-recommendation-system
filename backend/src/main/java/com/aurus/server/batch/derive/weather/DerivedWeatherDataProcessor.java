package com.aurus.server.batch.derive.weather;

import com.aurus.server.batch.aggregate.weather.AggregatedWeatherDataModel;

import org.jspecify.annotations.Nullable;
import org.springframework.batch.core.listener.StepExecutionListener;
import org.springframework.batch.infrastructure.item.ItemProcessor;

public class DerivedWeatherDataProcessor
        implements ItemProcessor<AggregatedWeatherDataModel, DerivedWeatherDataModel>, StepExecutionListener {

    @Override
    public @Nullable DerivedWeatherDataModel process(
            AggregatedWeatherDataModel model) throws Exception {

        float tempStress = clamp(model.getTempStress().value());

        float vpdStress = clamp(
                model.getVapourPressureDeficit().value() / 3.0f);

        float precipitationNorm = clamp(
                model.getPrecipitation().value() / 10.0f);

        float precipitationProbabilityNorm = clamp(
                model.getPrecipitationProbability().value() / 100.0f);

        float rainImpactIndexValue = clamp(
                (0.60f * precipitationProbabilityNorm)
                        + (0.40f * precipitationNorm));

        float rainCoolingFactor = rainImpactIndexValue;

        float heatStressIndexValue = clamp(
                ((0.65f * tempStress)
                        + (0.35f * vpdStress))
                        * (1f - (0.30f * rainCoolingFactor)));

        float waterBalanceIndexValue = clamp(
                (1.3f * model.getPrecipitation().value())
                        /
                        ((1.3f * model.getPrecipitation().value())
                                + model.getEvapotranspiration().value()
                                + 0.001f));

        float plantStressIndexValue = clamp(
                (0.15f * tempStress)
                        + (0.15f * vpdStress)
                        + (0.65f * (1f - waterBalanceIndexValue))
                        + (0.05f * rainImpactIndexValue));

        DerivedWeatherDataDTO plantStressIndex = new DerivedWeatherDataDTO(
                toFourDigitsDecimal(plantStressIndexValue),
                "normalized");

        DerivedWeatherDataDTO heatStressIndex = new DerivedWeatherDataDTO(
                toFourDigitsDecimal(heatStressIndexValue),
                "normalized");

        DerivedWeatherDataDTO rainImpactIndex = new DerivedWeatherDataDTO(
                toFourDigitsDecimal(rainImpactIndexValue),
                "normalized");

        DerivedWeatherDataDTO waterBalanceIndex = new DerivedWeatherDataDTO(
                toFourDigitsDecimal(waterBalanceIndexValue),
                "normalized");

        return new DerivedWeatherDataModel(
                plantStressIndex,
                heatStressIndex,
                rainImpactIndex,
                waterBalanceIndex,
                model.getId());
    }

    private float clamp(float value) {
        return Math.max(0f, Math.min(1f, value));
    }

    private float toFourDigitsDecimal(float value) {
        return Math.round(value * 10_000.0f) / 10_000.0f;
    }
}
