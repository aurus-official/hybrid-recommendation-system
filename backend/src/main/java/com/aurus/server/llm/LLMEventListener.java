package com.aurus.server.llm;

import com.fasterxml.jackson.core.JsonProcessingException;

import org.springframework.context.event.EventListener;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.stereotype.Component;

@EnableAsync
@Component
public class LLMEventListener {

    private final LLMRecommendationService llmRecommendationService;

    public LLMEventListener(LLMRecommendationService llmRecommendationService) {
        this.llmRecommendationService = llmRecommendationService;
    }

    @Async
    @EventListener
    public void listenLLMRecommendationReadyEvent(LLMRecommendationReadyEvent llmRecommendationReadyEvent)
            throws JsonProcessingException {
        llmRecommendationService
                .generateRecommendationsAndSaveToDb(llmRecommendationReadyEvent.engineEvaluationOutputDTO());
    }
}
