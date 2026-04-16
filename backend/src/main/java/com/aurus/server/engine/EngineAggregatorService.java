package com.aurus.server.engine;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

import com.aurus.server.llm.LLMEventPublisher;

import org.springframework.stereotype.Service;

@Service
public class EngineAggregatorService {

    private final LLMEventPublisher llmEventPublisher;

    public EngineAggregatorService(LLMEventPublisher llmEventPublisher) {
        this.llmEventPublisher = llmEventPublisher;
    }

    public void finalizeOutput(EngineAggregatorOutputDTO engineAggregatorOutputDTO, long derivedSensorId,
            long derivedWeatherId) {
        List<EngineCategoryOutputDTO> allCategoryOutputs = new ArrayList<>();

        EngineCategoryOutputDTO irrigationFinalOutput = engineAggregatorOutputDTO.irrigationOutputs().stream()
                .max(Comparator.comparingDouble(EngineCategoryOutputDTO::getScore)).get();
        allCategoryOutputs.add(irrigationFinalOutput);

        EngineCategoryOutputDTO soilNutrientFinalOutput = engineAggregatorOutputDTO.soilNutrientOutputs().stream()
                .max(Comparator.comparingDouble(EngineCategoryOutputDTO::getScore)).get();
        allCategoryOutputs.add(soilNutrientFinalOutput);

        EngineCategoryOutputDTO microClimateFinalOutput = engineAggregatorOutputDTO.microClimateOutputs().stream()
                .max(Comparator.comparingDouble(EngineCategoryOutputDTO::getScore)).get();
        allCategoryOutputs.add(microClimateFinalOutput);

        EngineCategoryOutputDTO cropOperationFinalOutput = engineAggregatorOutputDTO.cropOperationOutputs().stream()
                .max(Comparator.comparingDouble(EngineCategoryOutputDTO::getScore)).get();
        allCategoryOutputs.add(cropOperationFinalOutput);

        EngineEvaluationOutputDTO engineEvaluationOutputDTO = new EngineEvaluationOutputDTO(allCategoryOutputs,
                derivedSensorId, derivedWeatherId);

        System.out.println(irrigationFinalOutput.toString());
        System.out.println(soilNutrientFinalOutput.toString());
        System.out.println(microClimateFinalOutput.toString());
        System.out.println(cropOperationFinalOutput.toString());

        llmEventPublisher.publishLLMRecommendationReadyEvent(engineEvaluationOutputDTO);
    }
}
