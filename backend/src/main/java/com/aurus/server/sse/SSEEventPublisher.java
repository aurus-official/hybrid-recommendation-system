package com.aurus.server.sse;

import com.aurus.server.llm.LLMRecommendationModel;

import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Component;

@Component
public class SSEEventPublisher {
    private final ApplicationEventPublisher applicationEventPublisher;

    public SSEEventPublisher(ApplicationEventPublisher applicationEventPublisher) {
        this.applicationEventPublisher = applicationEventPublisher;
    }

    public void publishSSERealtimeDataUpdateEvent(LLMRecommendationModel llmRecommendationModel) {
        this.applicationEventPublisher.publishEvent(new SSEDataUpdateEvent(llmRecommendationModel));
    }

}
