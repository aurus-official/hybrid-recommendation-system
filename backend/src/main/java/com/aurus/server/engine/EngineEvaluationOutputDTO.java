package com.aurus.server.engine;

public record EngineEvaluationOutputDTO(
        EngineCategoryOutputDTO irrigationFinalOutput,
        EngineCategoryOutputDTO soilNutrientFinalOutput,
        EngineCategoryOutputDTO microClimateFinalOutput,
        EngineCategoryOutputDTO cropOperationFinalOutput) {
}
