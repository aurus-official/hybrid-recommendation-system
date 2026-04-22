package com.aurus.server.history;

import java.util.List;

import com.aurus.server.llm.LLMRecommendationDTO;

public record HistoryPageDTO(List<LLMRecommendationDTO> llmRecommendationDTOs, int pageCount) {

}
