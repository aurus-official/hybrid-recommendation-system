package com.aurus.server.llm;

import com.aurus.server.engine.EngineEvaluationOutputDTO;

public record LLMRecommendationReadyEvent(EngineEvaluationOutputDTO engineEvaluationOutputDTO) {

}
