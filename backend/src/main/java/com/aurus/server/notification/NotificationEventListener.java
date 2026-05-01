package com.aurus.server.notification;

import java.io.IOException;

import com.aurus.server.sse.SSEBroadcaster;

import org.springframework.context.event.EventListener;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.stereotype.Component;

@EnableAsync
@Component
public class NotificationEventListener {

    private final NotificationService notificationService;
    private final SSEBroadcaster sseBroadcaster;

    public NotificationEventListener(NotificationService notificationService, SSEBroadcaster sseBroadcaster) {
        this.notificationService = notificationService;
        this.sseBroadcaster = sseBroadcaster;
    }

    @Async
    @EventListener
    public void listenNotificationCriticalEvent(NotificationCriticalEvent notificationCriticalEvent)
            throws IOException {
        notificationService.startPushNotification(notificationCriticalEvent.notificationCriticalDataDTO());
    }

    @Async
    @EventListener
    public void listenNotificationDataUpdateEvent(NotificationDataUpdateEvent notificationDataUpdateEvent)
            throws IOException {
        sseBroadcaster.updateAndPushNotification();
    }
}
