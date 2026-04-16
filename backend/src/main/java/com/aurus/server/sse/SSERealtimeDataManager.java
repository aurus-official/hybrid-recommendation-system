package com.aurus.server.sse;

import com.aurus.server.batch.aggregate.weather.AggregatedWeatherDataModel;
import com.aurus.server.batch.aggregate.weather.AggregatedWeatherDataRepository;
import com.aurus.server.batch.derive.sensor.DerivedSensorDataModel;
import com.aurus.server.batch.derive.sensor.DerivedSensorDataRepository;
import com.aurus.server.batch.derive.weather.DerivedWeatherDataModel;
import com.aurus.server.batch.derive.weather.DerivedWeatherDataRepository;
import com.aurus.server.batch.process.weather.ProcessedWeatherDataModel;
import com.aurus.server.batch.process.weather.ProcessedWeatherDataRepository;
import com.aurus.server.llm.LLMRecommendationModel;

import org.springframework.stereotype.Service;

@Service
public class SSERealtimeDataManager {
    private final DerivedSensorDataRepository derivedSensorDataRepository;
    private final ProcessedWeatherDataRepository processedWeatherDataRepository;
    private final AggregatedWeatherDataRepository aggregatedWeatherDataRepository;
    private final DerivedWeatherDataRepository derivedWeatherDataRepository;

    private volatile DerivedSensorDataModel derivedSensorDataModel;
    private volatile DerivedWeatherDataModel derivedWeatherDataModel;
    private volatile ProcessedWeatherDataModel processedWeatherDataModel;
    private volatile AggregatedWeatherDataModel aggregatedWeatherDataModel;
    private volatile LLMRecommendationModel llmRecommendationModel;

    public SSERealtimeDataManager(DerivedSensorDataRepository derivedSensorDataRepository,
            ProcessedWeatherDataRepository processedWeatherDataRepository,
            AggregatedWeatherDataRepository aggregatedWeatherDataRepository,
            DerivedWeatherDataRepository derivedWeatherDataRepository) {
        this.derivedSensorDataRepository = derivedSensorDataRepository;
        this.processedWeatherDataRepository = processedWeatherDataRepository;
        this.aggregatedWeatherDataRepository = aggregatedWeatherDataRepository;
        this.derivedWeatherDataRepository = derivedWeatherDataRepository;
    }

    public SSERealtimeDataDTO getSEERealtimeData() {
        return new SSERealtimeDataDTO(
                this.derivedSensorDataModel,
                this.derivedWeatherDataModel,
                this.processedWeatherDataModel,
                this.llmRecommendationModel);
    }

    public void updateToLatestData(LLMRecommendationModel llmRecommendationModel) {
        this.llmRecommendationModel = llmRecommendationModel;
        this.derivedSensorDataModel = derivedSensorDataRepository
                .findById(this.llmRecommendationModel.getDerivedSensorDataId())
                .orElseGet(() -> new DerivedSensorDataModel());
        this.derivedWeatherDataModel = derivedWeatherDataRepository
                .findById(this.llmRecommendationModel.getDerivedWeatherDataId())
                .orElseGet(() -> new DerivedWeatherDataModel());
        this.aggregatedWeatherDataModel = aggregatedWeatherDataRepository
                .findById(this.derivedWeatherDataModel.getAggregatedWeatherDataId())
                .orElseGet(() -> new AggregatedWeatherDataModel());
        this.processedWeatherDataModel = processedWeatherDataRepository
                .findById(this.aggregatedWeatherDataModel.getProcessedWeatherDataId())
                .orElseGet(() -> new ProcessedWeatherDataModel());
        System.out.println(this.processedWeatherDataModel.getProcessedWeatherDataPointsHourly().size());
        System.out.println(this.processedWeatherDataModel.getProcessedWeatherDataPointsDaily().size());
    }

}
