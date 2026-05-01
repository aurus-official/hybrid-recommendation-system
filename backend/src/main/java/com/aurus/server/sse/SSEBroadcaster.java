package com.aurus.server.sse;

import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

import com.aurus.server.llm.LLMRecommendationModel;
import com.aurus.server.notification.NotificationDataManager;
import com.aurus.server.notification.NotificationDataModel;
import com.aurus.server.notification.NotificationService;
import com.aurus.server.shared.AllDataDTO;

import org.springframework.stereotype.Service;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

@Service
public class SSEBroadcaster {

    private final List<SseEmitter> clients = new CopyOnWriteArrayList<>();
    private final SSEDataManager sseDataManager;
    private final NotificationDataManager notificationDataManager;
    private final NotificationService notificationService;

    public SSEBroadcaster(SSEDataManager sseDataManager, NotificationService notificationService,
            NotificationDataManager notificationDataManager) {
        this.sseDataManager = sseDataManager;
        this.notificationDataManager = notificationDataManager;
        this.notificationService = notificationService;
    }

    public SseEmitter subscribe(String expoPushToken, String deviceId) {
        notificationService.addDeviceToNotificationService(expoPushToken, deviceId);
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

        List<NotificationDataModel> notificationModels = notificationDataManager.getTop5MostRecentNotifications();
        if (notificationModels.size() < 0) {
            try {
                emitter.send(SseEmitter.event()
                        .name("top-5-most-notifications")
                        .data(notificationModels));
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

    public void updateAndPushNotification() {
        notificationDataManager.updateToLatestData();
        List<NotificationDataModel> notificationModels = notificationDataManager.getTop5MostRecentNotifications();
        for (SseEmitter emitter : clients) {
            try {
                emitter.send(SseEmitter.event()
                        .name("top-5-most-notifications")
                        .data(notificationModels));
            } catch (Exception e) {
                emitter.complete();
                clients.remove(emitter);
            }
        }
    }
}
