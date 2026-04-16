package com.aurus.server.llm;

import com.aurus.server.engine.EngineEvaluationOutputDTO;

import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Component;

@Component
public class LLMEventPublisher {

    private final ApplicationEventPublisher applicationEventPublisher;

    public LLMEventPublisher(ApplicationEventPublisher applicationEventPublisher) {
        this.applicationEventPublisher = applicationEventPublisher;
    }

    public void publishLLMRecommendationReadyEvent(EngineEvaluationOutputDTO engineEvaluationOutputDTO) {
        this.applicationEventPublisher.publishEvent(new LLMRecommendationReadyEvent(engineEvaluationOutputDTO));
    }

}
