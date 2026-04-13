package com.aurus.server.batch.derive.weather;

import com.aurus.server.batch.aggregate.weather.AggregatedWeatherDataModel;

import org.jspecify.annotations.Nullable;
import org.springframework.batch.core.listener.StepExecutionListener;
import org.springframework.batch.infrastructure.item.ItemProcessor;

public class DerivedWeatherDataProcessor
        implements ItemProcessor<AggregatedWeatherDataModel, DerivedWeatherDataModel>, StepExecutionListener {

    @Override
    public @Nullable DerivedWeatherDataModel process(AggregatedWeatherDataModel model) throws Exception {
        float humidityStress = (100f - model.getHumidity().value()) / 100f;
        float vapourPressureDeficitNorm = model.getVapourPressureDeficit().value() / 3f;
        float precipitationNorm = model.getPrecipitation().value() / 10f;
        float precipitationProbabilityNorm = model.getPrecipitationProbability().value() / 100f;
        float evapotranspirationNorm = model.getEvapotranspiration().value() / 3f;

        float plantStressIndexValue = Math.max(0f, Math.min(1f, (0.5f * model.getTempStress().value())
                + (0.3f * humidityStress)
                + (0.2f * vapourPressureDeficitNorm)));

        float heatStressIndexValue = Math.max(0f,
                Math.min(1f, (0.7f * model.getTempStress().value()) + (0.3f * humidityStress)));

        float rainImpactIndexValue = Math.max(0f,
                Math.min(1f, (0.6f * precipitationProbabilityNorm) + (0.4f * precipitationNorm)));
        float waterBalanceIndexValue = Math.max(0f,
                Math.min(1f, (0.5f + (precipitationNorm - evapotranspirationNorm) / 2f)));

        DerivedWeatherDataDTO plantStressIndex = new DerivedWeatherDataDTO(toFourDigitsDecimal(plantStressIndexValue),
                "normalized");
        DerivedWeatherDataDTO heatStressIndex = new DerivedWeatherDataDTO(toFourDigitsDecimal(heatStressIndexValue),
                "normalized");
        DerivedWeatherDataDTO rainImpactIndex = new DerivedWeatherDataDTO(toFourDigitsDecimal(rainImpactIndexValue),
                "normalized");
        DerivedWeatherDataDTO waterBalanceIndex = new DerivedWeatherDataDTO(toFourDigitsDecimal(waterBalanceIndexValue),
                "normalized");

        DerivedWeatherDataModel derivedWeatherDataModel = new DerivedWeatherDataModel(plantStressIndex, heatStressIndex,
                rainImpactIndex, waterBalanceIndex, model.getId());

        return derivedWeatherDataModel;
    }

    private float toFourDigitsDecimal(float value) {
        return Math.round(value * 10_000.0f) / 10_000.0f;
    }
}
