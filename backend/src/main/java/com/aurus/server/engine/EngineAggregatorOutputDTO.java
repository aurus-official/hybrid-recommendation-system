package com.aurus.server.engine;

import java.util.List;

public record EngineAggregatorOutputDTO(
        List<EngineCategoryOutputDTO> irrigationOutputs,
        List<EngineCategoryOutputDTO> soilNutrientOutputs,
        List<EngineCategoryOutputDTO> microClimateOutputs,
        List<EngineCategoryOutputDTO> cropOperationOutputs) {
}
