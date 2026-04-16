package com.aurus.server.sse;

import com.aurus.server.llm.LLMRecommendationModel;

public record SSERealtimeDataUpdateEvent(LLMRecommendationModel llmRecommendationModel) {

}
