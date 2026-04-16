package com.aurus.server.sse;

import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

import com.aurus.server.llm.LLMRecommendationModel;

import org.springframework.stereotype.Service;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

@Service
public class SSEBroadcaster {

    private final List<SseEmitter> clients = new CopyOnWriteArrayList<>();
    private final SSERealtimeDataManager sseRealtimeDataManager;

    public SSEBroadcaster(SSERealtimeDataManager sseRealtimeDataManager) {
        this.sseRealtimeDataManager = sseRealtimeDataManager;
    }

    public SseEmitter subscribe() {
        SseEmitter emitter = new SseEmitter(0L);
        clients.add(emitter);

        emitter.onCompletion(() -> clients.remove(emitter));
        emitter.onTimeout(() -> clients.remove(emitter));
        emitter.onError(e -> clients.remove(emitter));

        return emitter;
    }

    public void updateAndPushRealtimeData(LLMRecommendationModel llmRecommendationModel) {
        sseRealtimeDataManager.updateToLatestData(llmRecommendationModel);
        SSERealtimeDataDTO sseRealtimeDataDTO = sseRealtimeDataManager.getSEERealtimeData();

        for (SseEmitter emitter : clients) {
            try {
                emitter.send(SseEmitter.event()
                        .name("sse-realtime-data")
                        .data(sseRealtimeDataDTO));
            } catch (Exception e) {
                emitter.complete();
                clients.remove(emitter);
            }
        }
    }
}
