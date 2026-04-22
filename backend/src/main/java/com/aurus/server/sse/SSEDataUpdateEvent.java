package com.aurus.server.sse;

import com.aurus.server.llm.LLMRecommendationModel;

public record SSEDataUpdateEvent(LLMRecommendationModel llmRecommendationModel) {

}
