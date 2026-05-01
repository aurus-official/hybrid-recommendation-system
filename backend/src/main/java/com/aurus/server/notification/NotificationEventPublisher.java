package com.aurus.server.notification;

import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Component;

@Component
public class NotificationEventPublisher {
    private final ApplicationEventPublisher applicationEventPublisher;

    public NotificationEventPublisher(ApplicationEventPublisher applicationEventPublisher) {
        this.applicationEventPublisher = applicationEventPublisher;
    }

    public void publishNotificationCriticalEvent(NotificationCriticalDataDTO notificationCriticalDataDTO) {
        this.applicationEventPublisher.publishEvent(new NotificationCriticalEvent(notificationCriticalDataDTO));
    }

    public void publishNotificationDataUpdateEvent() {
        this.applicationEventPublisher.publishEvent(new NotificationDataUpdateEvent());
    }

}
