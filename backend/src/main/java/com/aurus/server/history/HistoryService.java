package com.aurus.server.history;

import java.util.List;

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
import com.aurus.server.llm.LLMRecommendationDTO;
import com.aurus.server.llm.LLMRecommendationModel;
import com.aurus.server.llm.LLMRecommendationRepository;
import com.aurus.server.shared.AllDataDTO;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

@Service
public class HistoryService {

    private final int PAGE_SIZE = 8;
    private final DerivedSensorDataRepository derivedSensorDataRepository;
    private final ProcessedWeatherDataRepository processedWeatherDataRepository;
    private final AggregatedSensorDataRepository aggregatedSensorDataRepository;
    private final AggregatedWeatherDataRepository aggregatedWeatherDataRepository;
    private final DerivedWeatherDataRepository derivedWeatherDataRepository;
    private final LLMRecommendationRepository llmRecommendationRepository;

    public HistoryService(DerivedSensorDataRepository derivedSensorDataRepository,
            ProcessedWeatherDataRepository processedWeatherDataRepository,
            AggregatedSensorDataRepository aggregatedSensorDataRepository,
            AggregatedWeatherDataRepository aggregatedWeatherDataRepository,
            DerivedWeatherDataRepository derivedWeatherDataRepository,
            LLMRecommendationRepository llmRecommendationRepository) {
        this.derivedSensorDataRepository = derivedSensorDataRepository;
        this.processedWeatherDataRepository = processedWeatherDataRepository;
        this.aggregatedSensorDataRepository = aggregatedSensorDataRepository;
        this.aggregatedWeatherDataRepository = aggregatedWeatherDataRepository;
        this.derivedWeatherDataRepository = derivedWeatherDataRepository;
        this.llmRecommendationRepository = llmRecommendationRepository;
    }

    public List<LLMRecommendationDTO> getRecommendationPage(int pageNumber) {
        Pageable pageable = PageRequest.of(pageNumber - 1, PAGE_SIZE, Sort.by(Sort.Order.desc("id")));
        Page<LLMRecommendationModel> recommendationModels = llmRecommendationRepository.findAll(pageable);

        List<LLMRecommendationDTO> llmRecommendationDTOs = recommendationModels.toList().stream().map(model -> {
            return new LLMRecommendationDTO(model.getId(), model.getCreatedAt());
        }).toList();

        return llmRecommendationDTOs;
    }

    public AllDataDTO getAllDataDTO(long id) {
        LLMRecommendationModel llmRecommendationModel = llmRecommendationRepository.findById(id).get();

        DerivedSensorDataModel derivedSensorDataModel = derivedSensorDataRepository
                .findById(llmRecommendationModel.getDerivedSensorDataId())
                .orElseGet(() -> new DerivedSensorDataModel());
        AggregatedSensorDataModel aggregatedSensorDataModel = aggregatedSensorDataRepository
                .findById(derivedSensorDataModel.getAggregatedSensorDataId())
                .orElseGet(() -> new AggregatedSensorDataModel());
        DerivedWeatherDataModel derivedWeatherDataModel = derivedWeatherDataRepository
                .findById(llmRecommendationModel.getDerivedWeatherDataId())
                .orElseGet(() -> new DerivedWeatherDataModel());
        AggregatedWeatherDataModel aggregatedWeatherDataModel = aggregatedWeatherDataRepository
                .findById(derivedWeatherDataModel.getAggregatedWeatherDataId())
                .orElseGet(() -> new AggregatedWeatherDataModel());
        ProcessedWeatherDataModel processedWeatherDataModel = processedWeatherDataRepository
                .findById(aggregatedWeatherDataModel.getProcessedWeatherDataId())
                .orElseGet(() -> new ProcessedWeatherDataModel());

        return new AllDataDTO(
                derivedSensorDataModel,
                derivedWeatherDataModel,
                aggregatedSensorDataModel,
                aggregatedWeatherDataModel,
                processedWeatherDataModel,
                llmRecommendationModel);
    }

}
