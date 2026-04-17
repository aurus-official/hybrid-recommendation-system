package com.aurus.server.llm;

import java.util.HashMap;
import java.util.Map;

import com.aurus.server.engine.EngineCategoryOutputDTO;
import com.aurus.server.engine.EngineEvaluationOutputDTO;
import com.aurus.server.shared.CategoryType;
import com.aurus.server.shared.SeverityLevel;
import com.aurus.server.sse.SSEEventPublisher;
import com.fasterxml.jackson.core.JsonProcessingException;

import org.springframework.stereotype.Service;

@Service
public class LLMRecommendationService {

    private final LLMGenerator llmGenerator;
    private final LLMPromptBuilder llmPromptBuilder;
    private final LLMRecommendationRepository llmRecommendationRepository;
    private final SSEEventPublisher sseEventPublisher;

    public LLMRecommendationService(LLMGenerator llmGenerator, LLMPromptBuilder llmPromptBuilder,
            LLMRecommendationRepository llmRecommendationRepository, SSEEventPublisher sseEventPublisher) {
        this.llmGenerator = llmGenerator;
        this.llmPromptBuilder = llmPromptBuilder;
        this.llmRecommendationRepository = llmRecommendationRepository;
        this.sseEventPublisher = sseEventPublisher;
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

        llmRecommendationRepository.save(llmRecommendationModel);
        sseEventPublisher.publishSSERealtimeDataUpdateEvent(llmRecommendationModel);
    }
}
