package com.aurus.server.sse;

import com.aurus.server.batch.derive.sensor.DerivedSensorDataModel;
import com.aurus.server.batch.derive.weather.DerivedWeatherDataModel;
import com.aurus.server.batch.process.weather.ProcessedWeatherDataModel;
import com.aurus.server.llm.LLMRecommendationModel;

public record SSEDataDTO(
        DerivedSensorDataModel derivedSensorDataModel,
        DerivedWeatherDataModel derivedWeatherDataModel,
        ProcessedWeatherDataModel processedWeatherDataModel,
        LLMRecommendationModel llmRecommendationModel) {

}
