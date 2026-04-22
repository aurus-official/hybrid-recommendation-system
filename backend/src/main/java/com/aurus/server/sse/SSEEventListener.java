package com.aurus.server.sse;

import org.springframework.context.event.EventListener;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.stereotype.Component;

@Component
@EnableAsync
public class SSEEventListener {
    private final SSEBroadcaster sseBroadcaster;

    public SSEEventListener(SSEBroadcaster sseBroadcaster) {
        this.sseBroadcaster = sseBroadcaster;
    }

    @EventListener
    @Async
    public void listenSSERealtimeDataUpdateEvent(SSEDataUpdateEvent sseDataUpdateEvent) {
        sseBroadcaster.updateAndPushRealtimeData(sseDataUpdateEvent.llmRecommendationModel());

    }
}
