package com.aurus.server.llm;

import java.util.List;

public record LLMPageRecommendationDTO(List<LLMRecommendationSummaryDTO> llmRecommendationSummaryDTOs, int pageCount) {

}
