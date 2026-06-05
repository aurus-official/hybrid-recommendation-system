package com.aurus.server.llm;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
import com.aurus.server.engine.EngineCategoryOutputDTO;
import com.aurus.server.engine.EngineEvaluationOutputDTO;
import com.aurus.server.ingestion.hardware_status.HardwareStatusModel;
import com.aurus.server.ingestion.hardware_status.HardwareStatusRepository;
import com.aurus.server.notification.NotificationEventPublisher;
import com.aurus.server.notification.recommendation.NotificationHighPriorityRecommendationDTO;
import com.aurus.server.reading_status.ReadingStatusModel;
import com.aurus.server.reading_status.ReadingStatusRepository;
import com.aurus.server.shared.AllDataDTO;
import com.aurus.server.shared.CategoryType;
import com.aurus.server.shared.SeverityLevel;
import com.aurus.server.sse.SSEEventPublisher;
import com.fasterxml.jackson.core.JsonProcessingException;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

@Service
public class LLMRecommendationService {

    private final int PAGE_SIZE = 8;
    private final LLMGenerator llmGenerator;
    private final LLMPromptBuilder llmPromptBuilder;
    private final SSEEventPublisher sseEventPublisher;
    private final NotificationEventPublisher notificationEventPublisher;
    private final DerivedSensorDataRepository derivedSensorDataRepository;
    private final ProcessedWeatherDataRepository processedWeatherDataRepository;
    private final AggregatedSensorDataRepository aggregatedSensorDataRepository;
    private final AggregatedWeatherDataRepository aggregatedWeatherDataRepository;
    private final DerivedWeatherDataRepository derivedWeatherDataRepository;
    private final LLMRecommendationRepository llmRecommendationRepository;
    private final HardwareStatusRepository hardwareStatusRepository;
    private final ReadingStatusRepository readingStatusRepository;

    public LLMRecommendationService(LLMGenerator llmGenerator, LLMPromptBuilder llmPromptBuilder,
            SSEEventPublisher sseEventPublisher, NotificationEventPublisher notificationEventPublisher,
            DerivedSensorDataRepository derivedSensorDataRepository,
            ProcessedWeatherDataRepository processedWeatherDataRepository,
            AggregatedSensorDataRepository aggregatedSensorDataRepository,
            AggregatedWeatherDataRepository aggregatedWeatherDataRepository,
            DerivedWeatherDataRepository derivedWeatherDataRepository,
            LLMRecommendationRepository llmRecommendationRepository,
            HardwareStatusRepository hardwareStatusRepository,
            ReadingStatusRepository readingStatusRepository) {
        this.llmGenerator = llmGenerator;
        this.llmPromptBuilder = llmPromptBuilder;
        this.sseEventPublisher = sseEventPublisher;
        this.notificationEventPublisher = notificationEventPublisher;
        this.derivedSensorDataRepository = derivedSensorDataRepository;
        this.processedWeatherDataRepository = processedWeatherDataRepository;
        this.aggregatedSensorDataRepository = aggregatedSensorDataRepository;
        this.aggregatedWeatherDataRepository = aggregatedWeatherDataRepository;
        this.derivedWeatherDataRepository = derivedWeatherDataRepository;
        this.llmRecommendationRepository = llmRecommendationRepository;
        this.hardwareStatusRepository = hardwareStatusRepository;
        this.readingStatusRepository = readingStatusRepository;
    }

    public void generateRecommendationsAndSaveToDb(EngineEvaluationOutputDTO engineEvaluationOutputDTO)
            throws JsonProcessingException {
        Map<CategoryType, String> outputMap = new HashMap<>();
        Map<CategoryType, SeverityLevel> severityMap = new HashMap<>();

        for (EngineCategoryOutputDTO engineCategoryOutputDTO : engineEvaluationOutputDTO.allCategoryOutputs()) {
            String prompt = llmPromptBuilder.buildPrompt(engineCategoryOutputDTO);

            outputMap.put(
                    engineCategoryOutputDTO.getCategoryType(),
                    llmGenerator.generateRecommendation(prompt));

            severityMap.put(
                    engineCategoryOutputDTO.getCategoryType(),
                    engineCategoryOutputDTO.getSeverityLevel());
        }

        LLMRecommendationModel llmRecommendationModel = new LLMRecommendationModel(
                outputMap.get(CategoryType.IRRIGATION), severityMap.get(CategoryType.IRRIGATION).getNum(),
                outputMap.get(CategoryType.SOIL_NUTRIENT), severityMap.get(CategoryType.SOIL_NUTRIENT).getNum(),
                outputMap.get(CategoryType.MICROCLIMATE), severityMap.get(CategoryType.MICROCLIMATE).getNum(),
                outputMap.get(CategoryType.CROP_OPERATION), severityMap.get(CategoryType.CROP_OPERATION).getNum(),
                engineEvaluationOutputDTO.derivedSensorId(),
                engineEvaluationOutputDTO.derivedWeatherId());

        LLMRecommendationModel addedLLMRecommendationModel = llmRecommendationRepository.save(llmRecommendationModel);

        sseEventPublisher.publishSSERealtimeDataUpdateEvent(llmRecommendationModel);

        if (severityMap.values().stream()
                .anyMatch(value -> value == SeverityLevel.CRITICAL)) {
            notificationEventPublisher
                    .publishNotificationHighPriorityRecommendationEvent(new NotificationHighPriorityRecommendationDTO(
                            addedLLMRecommendationModel.getCreatedAt(), addedLLMRecommendationModel.getId(),
                            SeverityLevel.CRITICAL));
            return;
        }

        if (severityMap.values().stream()
                .anyMatch(value -> value == SeverityLevel.HIGH)) {
            notificationEventPublisher
                    .publishNotificationHighPriorityRecommendationEvent(new NotificationHighPriorityRecommendationDTO(
                            addedLLMRecommendationModel.getCreatedAt(), addedLLMRecommendationModel.getId(),
                            SeverityLevel.HIGH));
            return;
        }

    }

    public LLMPageRecommendationDTO getRecommendationPage(int pageNumber) {
        Pageable pageable = PageRequest.of(pageNumber - 1, PAGE_SIZE, Sort.by(Sort.Order.desc("id")));
        Page<LLMRecommendationModel> recommendationModelsPage = llmRecommendationRepository.findAll(pageable);

        List<LLMRecommendationSummaryDTO> llmRecommendationSummaryDTOs = recommendationModelsPage.toList().stream()
                .map(model -> {
                    return new LLMRecommendationSummaryDTO(model.getId(), model.getCreatedAt());
                }).toList();

        return new LLMPageRecommendationDTO(llmRecommendationSummaryDTOs, recommendationModelsPage.getTotalPages());
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

        HardwareStatusModel hardwareStatusModel = hardwareStatusRepository.findFirstByOrderByIdDesc()
                .orElseGet(() -> new HardwareStatusModel());
        ReadingStatusModel readingStatusModel = readingStatusRepository.findFirstByOrderByIdDesc()
                .orElseGet(() -> new ReadingStatusModel());

        return new AllDataDTO(
                derivedSensorDataModel,
                derivedWeatherDataModel,
                aggregatedSensorDataModel,
                aggregatedWeatherDataModel,
                processedWeatherDataModel,
                llmRecommendationModel,
                hardwareStatusModel,
                readingStatusModel);
    }
}
