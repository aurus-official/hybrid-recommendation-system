package com.aurus.server.engine;

import java.util.Comparator;

import org.springframework.stereotype.Service;

@Service
public class EngineAggregatorService {

    public void finalizeOutput(EngineAggregatorOutputDTO engineAggregatorOutputDTO) {
        EngineCategoryOutputDTO irrigationFinalOutput = engineAggregatorOutputDTO.irrigationOutputs().stream()
                .max(Comparator.comparingDouble(EngineCategoryOutputDTO::getScore)).get();
        EngineCategoryOutputDTO soilNutrientFinalOutput = engineAggregatorOutputDTO.soilNutrientOutputs().stream()
                .max(Comparator.comparingDouble(EngineCategoryOutputDTO::getScore)).get();
        EngineCategoryOutputDTO microClimateFinalOutput = engineAggregatorOutputDTO.microClimateOutputs().stream()
                .max(Comparator.comparingDouble(EngineCategoryOutputDTO::getScore)).get();
        EngineCategoryOutputDTO cropOperationFinalOutput = engineAggregatorOutputDTO.cropOperationOutputs().stream()
                .max(Comparator.comparingDouble(EngineCategoryOutputDTO::getScore)).get();

        EngineEvaluationOutputDTO engineEvaluationOutputDTO = new EngineEvaluationOutputDTO(
                irrigationFinalOutput,
                soilNutrientFinalOutput,
                microClimateFinalOutput,
                cropOperationFinalOutput);

        System.out.println(irrigationFinalOutput.toString());
        System.out.println(soilNutrientFinalOutput.toString());
        System.out.println(microClimateFinalOutput.toString());
        System.out.println(cropOperationFinalOutput.toString());
    }
}
