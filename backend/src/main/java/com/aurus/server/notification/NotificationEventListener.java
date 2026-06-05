package com.aurus.server.notification;

import java.io.IOException;

import com.aurus.server.notification.health_status.NotificationHighPriorityHealthStatusEvent;
import com.aurus.server.notification.health_status.NotificationHighPriorityHealthStatusService;
import com.aurus.server.notification.recommendation.NotificationHighPriorityRecommendationEvent;
import com.aurus.server.notification.recommendation.NotificationHighPriorityRecommendationService;
import com.aurus.server.sse.SSEBroadcaster;

import org.springframework.context.event.EventListener;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.stereotype.Component;

@EnableAsync
@Component
public class NotificationEventListener {

    private final NotificationHighPriorityRecommendationService notificationHighPriorityRecommendationService;
    private final NotificationHighPriorityHealthStatusService notificationHighPriorityHealthStatusService;
    private final SSEBroadcaster sseBroadcaster;

    public NotificationEventListener(
            NotificationHighPriorityRecommendationService notificationHighPriorityRecommendationService,
            NotificationHighPriorityHealthStatusService notificationHighPriorityHealthStatusService,
            SSEBroadcaster sseBroadcaster) {
        this.notificationHighPriorityRecommendationService = notificationHighPriorityRecommendationService;
        this.notificationHighPriorityHealthStatusService = notificationHighPriorityHealthStatusService;
        this.sseBroadcaster = sseBroadcaster;
    }

    @Async
    @EventListener
    public void listenNotificationHighPriorityRecommendationEvent(
            NotificationHighPriorityRecommendationEvent notificationHighPriorityRecommendationEvent)
            throws IOException {
        notificationHighPriorityRecommendationService.startPushNotification(
                notificationHighPriorityRecommendationEvent.notificationHighPriorityRecommendationDTO());
    }

    @Async
    @EventListener
    public void listenNotificationHighPriorityHealthStatusEvent(
            NotificationHighPriorityHealthStatusEvent notificationHighPriorityHealthStatusEvent)
            throws IOException {
        notificationHighPriorityHealthStatusService.startPushNotification(
                notificationHighPriorityHealthStatusEvent.notificationHighPriorityHealthStatusDTO());
    }

    @Async
    @EventListener
    public void listenNotificationUpdateEvent(NotificationUpdateEvent notificationUpdateEvent)
            throws IOException {
        sseBroadcaster.updateAndPushNotification();
    }

}
