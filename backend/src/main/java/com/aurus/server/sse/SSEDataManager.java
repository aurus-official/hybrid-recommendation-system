package com.aurus.server.sse;

import java.util.Optional;

import jakarta.annotation.PostConstruct;

import com.aurus.server.batch.aggregate.sensor.AggregatedSensorDataModel;
import com.aurus.server.batch.aggregate.sensor.AggregatedSensorDataRepository;
import com.aurus.server.batch.aggregate.weather.AggregatedWeatherDataModel;
import com.aurus.server.batch.aggregate.weather.AggregatedWeatherDataRepository;
import com.aurus.server.batch.derive.sensor.DerivedSensorDataModel;
import com.aurus.server.batch.derive.sensor.DerivedSensorDataRepository;
import com.aurus.server.batch.derive.weather.DerivedWeatherDataModel;
import com.aurus.server.batch.derive.weather.DerivedWeatherDataRepository;
import com.aurus.server.batch.process.weather.ProcessedWeatherDataModel;
import com.aurus.server.batch.process.weather.ProcessedWeatherDataRepository;
import com.aurus.server.llm.LLMRecommendationModel;
import com.aurus.server.llm.LLMRecommendationRepository;
import com.aurus.server.shared.AllDataDTO;

import org.springframework.stereotype.Service;

@Service
public class SSEDataManager {
    private final DerivedSensorDataRepository derivedSensorDataRepository;
    private final ProcessedWeatherDataRepository processedWeatherDataRepository;
    private final AggregatedSensorDataRepository aggregatedSensorDataRepository;
    private final AggregatedWeatherDataRepository aggregatedWeatherDataRepository;
    private final DerivedWeatherDataRepository derivedWeatherDataRepository;
    private final LLMRecommendationRepository llmRecommendationRepository;

    private volatile DerivedSensorDataModel derivedSensorDataModel;
    private volatile DerivedWeatherDataModel derivedWeatherDataModel;
    private volatile ProcessedWeatherDataModel processedWeatherDataModel;
    private volatile AggregatedSensorDataModel aggregatedSensorDataModel;
    private volatile AggregatedWeatherDataModel aggregatedWeatherDataModel;
    private volatile LLMRecommendationModel llmRecommendationModel;

    public SSEDataManager(DerivedSensorDataRepository derivedSensorDataRepository,
            ProcessedWeatherDataRepository processedWeatherDataRepository,
            AggregatedWeatherDataRepository aggregatedWeatherDataRepository,
            DerivedWeatherDataRepository derivedWeatherDataRepository,
            LLMRecommendationRepository llmRecommendationRepository,
            AggregatedSensorDataRepository aggregatedSensorDataRepository) {
        this.derivedSensorDataRepository = derivedSensorDataRepository;
        this.processedWeatherDataRepository = processedWeatherDataRepository;
        this.aggregatedWeatherDataRepository = aggregatedWeatherDataRepository;
        this.derivedWeatherDataRepository = derivedWeatherDataRepository;
        this.llmRecommendationRepository = llmRecommendationRepository;
        this.aggregatedSensorDataRepository = aggregatedSensorDataRepository;

    }

    public AllDataDTO getAllDataDTO() {
        if (this.derivedSensorDataModel == null ||
                this.derivedWeatherDataModel == null ||
                this.aggregatedSensorDataModel == null ||
                this.aggregatedWeatherDataModel == null ||
                this.processedWeatherDataModel == null ||
                this.llmRecommendationModel == null) {
            return null;
        }

        return new AllDataDTO(
                this.derivedSensorDataModel,
                this.derivedWeatherDataModel,
                this.aggregatedSensorDataModel,
                this.aggregatedWeatherDataModel,
                this.processedWeatherDataModel,
                this.llmRecommendationModel);
    }

    public void updateToLatestData(LLMRecommendationModel llmRecommendationModel) {
        this.llmRecommendationModel = llmRecommendationModel;

        this.derivedSensorDataModel = derivedSensorDataRepository
                .findById(this.llmRecommendationModel.getDerivedSensorDataId())
                .orElseGet(() -> new DerivedSensorDataModel());
        this.aggregatedSensorDataModel = aggregatedSensorDataRepository
                .findById(this.derivedSensorDataModel.getAggregatedSensorDataId())
                .orElseGet(() -> new AggregatedSensorDataModel());
        this.derivedWeatherDataModel = derivedWeatherDataRepository
                .findById(this.llmRecommendationModel.getDerivedWeatherDataId())
                .orElseGet(() -> new DerivedWeatherDataModel());
        this.aggregatedWeatherDataModel = aggregatedWeatherDataRepository
                .findById(this.derivedWeatherDataModel.getAggregatedWeatherDataId())
                .orElseGet(() -> new AggregatedWeatherDataModel());
        this.processedWeatherDataModel = processedWeatherDataRepository
                .findById(this.aggregatedWeatherDataModel.getProcessedWeatherDataId())
                .orElseGet(() -> new ProcessedWeatherDataModel());
    }

    @PostConstruct
    private void retrieveLatestData() {
        Optional<LLMRecommendationModel> llmRecommendationModelOptional = this.llmRecommendationRepository
                .findFirstByOrderByCreatedAtDesc();

        if (llmRecommendationModelOptional.isPresent()) {

            LLMRecommendationModel latestLLMRecommendationModel = llmRecommendationModelOptional.get();
            this.llmRecommendationModel = latestLLMRecommendationModel;

            this.derivedSensorDataModel = derivedSensorDataRepository
                    .findById(this.llmRecommendationModel.getDerivedSensorDataId())
                    .orElseGet(() -> new DerivedSensorDataModel());
            this.aggregatedSensorDataModel = aggregatedSensorDataRepository
                    .findById(this.derivedSensorDataModel.getAggregatedSensorDataId())
                    .orElseGet(() -> new AggregatedSensorDataModel());
            this.derivedWeatherDataModel = derivedWeatherDataRepository
                    .findById(this.llmRecommendationModel.getDerivedWeatherDataId())
                    .orElseGet(() -> new DerivedWeatherDataModel());
            this.aggregatedWeatherDataModel = aggregatedWeatherDataRepository
                    .findById(this.derivedWeatherDataModel.getAggregatedWeatherDataId())
                    .orElseGet(() -> new AggregatedWeatherDataModel());
            this.processedWeatherDataModel = processedWeatherDataRepository
                    .findById(this.aggregatedWeatherDataModel.getProcessedWeatherDataId())
                    .orElseGet(() -> new ProcessedWeatherDataModel());
        }
    }

}
