package com.aurus.server.shared;

import com.aurus.server.batch.aggregate.sensor.AggregatedSensorDataModel;
import com.aurus.server.batch.aggregate.weather.AggregatedWeatherDataModel;
import com.aurus.server.batch.derive.sensor.DerivedSensorDataModel;
import com.aurus.server.batch.derive.weather.DerivedWeatherDataModel;
import com.aurus.server.batch.process.weather.ProcessedWeatherDataModel;
import com.aurus.server.llm.LLMRecommendationModel;

public record AllDataDTO(
        DerivedSensorDataModel derivedSensorDataModel,
        DerivedWeatherDataModel derivedWeatherDataModel,
        AggregatedSensorDataModel aggregatedSensorDataModel,
        AggregatedWeatherDataModel aggregatedWeatherDataModel,
        ProcessedWeatherDataModel processedWeatherDataModel,
        LLMRecommendationModel llmRecommendationModel) {

}
