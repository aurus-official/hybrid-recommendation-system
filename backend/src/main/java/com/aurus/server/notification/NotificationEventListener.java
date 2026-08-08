package com.aurus.server.notification;

import java.io.IOException;

import com.aurus.server.notification.hardware_status.NotificationHighPriorityHardwareStatusEvent;
import com.aurus.server.notification.hardware_status.NotificationHighPriorityHardwareStatusService;
import com.aurus.server.notification.reading_status.NotificationHighPriorityReadingStatusEvent;
import com.aurus.server.notification.reading_status.NotificationHighPriorityReadingStatusService;
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
    private final NotificationHighPriorityReadingStatusService notificationHighPriorityReadingStatusService;
    private final NotificationHighPriorityHardwareStatusService notificationHighPriorityHardwareStatusService;
    private final SSEBroadcaster sseBroadcaster;

    public NotificationEventListener(
            NotificationHighPriorityRecommendationService notificationHighPriorityRecommendationService,
            NotificationHighPriorityReadingStatusService notificationHighPriorityReadingStatusService,
            NotificationHighPriorityHardwareStatusService notificationHighPriorityHardwareStatusService,
            SSEBroadcaster sseBroadcaster) {
        this.notificationHighPriorityRecommendationService = notificationHighPriorityRecommendationService;
        this.notificationHighPriorityReadingStatusService = notificationHighPriorityReadingStatusService;
        this.notificationHighPriorityHardwareStatusService = notificationHighPriorityHardwareStatusService;
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
    public void listenNotificationHighPriorityReadingStatusEvent(
            NotificationHighPriorityReadingStatusEvent notificationHighPriorityReadingStatusEvent)
            throws IOException {
        notificationHighPriorityReadingStatusService.startPushNotification(
                notificationHighPriorityReadingStatusEvent.notificationHighPriorityReadingStatusDTO());
    }

    @Async
    @EventListener
    public void listenNotificationHighPriorityHardwareStatusEvent(
            NotificationHighPriorityHardwareStatusEvent notificationHighPriorityHardwareStatusEvent)
            throws IOException {
        notificationHighPriorityHardwareStatusService.startPushNotification(
                notificationHighPriorityHardwareStatusEvent.notificationHighPriorityHardwareStatusDTO());
    }

    @Async
    @EventListener
    public void listenNotificationUpdateEvent(NotificationUpdateEvent notificationUpdateEvent)
            throws IOException {
        sseBroadcaster.updateAndPushNotification();
    }

}
