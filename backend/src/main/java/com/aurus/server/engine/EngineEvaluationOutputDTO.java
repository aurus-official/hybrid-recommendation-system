package com.aurus.server.engine;

import java.util.List;

public record EngineEvaluationOutputDTO(
        List<EngineCategoryOutputDTO> allCategoryOutputs,
        long derivedSensorId,
        long derivedWeatherId) {
}
