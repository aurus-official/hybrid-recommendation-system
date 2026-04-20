package com.aurus.server.sse;

import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

import com.aurus.server.llm.LLMRecommendationModel;
import com.aurus.server.shared.AllDataDTO;

import org.springframework.stereotype.Service;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

@Service
public class SSEBroadcaster {

    private final List<SseEmitter> clients = new CopyOnWriteArrayList<>();
    private final SSEDataManager sseDataManager;

    public SSEBroadcaster(SSEDataManager sseDataManager) {
        this.sseDataManager = sseDataManager;
    }

    public SseEmitter subscribe() {
        SseEmitter emitter = new SseEmitter(0L);
        clients.add(emitter);

        emitter.onCompletion(() -> clients.remove(emitter));
        emitter.onTimeout(() -> clients.remove(emitter));
        emitter.onError(e -> clients.remove(emitter));

        AllDataDTO allDataDTO = sseDataManager.getAllDataDTO();

        if (allDataDTO != null) {
            try {
                emitter.send(SseEmitter.event()
                        .name("all-realtime-data")
                        .data(sseDataManager.getAllDataDTO()));
            } catch (Exception e) {
                emitter.complete();
                clients.remove(emitter);
            }
        }

        return emitter;
    }

    public void updateAndPushRealtimeData(LLMRecommendationModel llmRecommendationModel) {
        sseDataManager.updateToLatestData(llmRecommendationModel);
        AllDataDTO allDataDTO = sseDataManager.getAllDataDTO();

        for (SseEmitter emitter : clients) {
            try {
                emitter.send(SseEmitter.event()
                        .name("all-realtime-data")
                        .data(allDataDTO));
            } catch (Exception e) {
                emitter.complete();
                clients.remove(emitter);
            }
        }
    }
}
