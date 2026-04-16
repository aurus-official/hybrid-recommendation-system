package com.aurus.server.llm;

import java.util.HashMap;
import java.util.Map;

import com.aurus.server.engine.EngineCategoryOutputDTO;
import com.aurus.server.engine.EngineEvaluationOutputDTO;
import com.aurus.server.shared.CategoryType;
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
        Map<CategoryType, String> futures = new HashMap<>();

        for (EngineCategoryOutputDTO engineCategoryOutputDTO : engineEvaluationOutputDTO.allCategoryOutputs()) {
            String prompt = llmPromptBuilder.buildPrompt(engineCategoryOutputDTO);

            futures.put(
                    engineCategoryOutputDTO.getCategoryType(),
                    llmGenerator.generateRecommendation(prompt));
        }

        LLMRecommendationModel llmRecommendationModel = new LLMRecommendationModel(
                futures.get(CategoryType.IRRIGATION),
                futures.get(CategoryType.SOIL_NUTRIENT),
                futures.get(CategoryType.MICROCLIMATE),
                futures.get(CategoryType.CROP_OPERATION),
                engineEvaluationOutputDTO.derivedSensorId(),
                engineEvaluationOutputDTO.derivedWeatherId());

        llmRecommendationRepository.save(llmRecommendationModel);
        sseEventPublisher.publishSSERealtimeDataUpdateEvent(llmRecommendationModel);
    }
}
