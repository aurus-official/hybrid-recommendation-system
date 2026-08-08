package com.aurus.server.notification;

import com.aurus.server.notification.hardware_status.NotificationHighPriorityHardwareStatusDTO;
import com.aurus.server.notification.hardware_status.NotificationHighPriorityHardwareStatusEvent;
import com.aurus.server.notification.reading_status.NotificationHighPriorityReadingStatusDTO;
import com.aurus.server.notification.reading_status.NotificationHighPriorityReadingStatusEvent;
import com.aurus.server.notification.recommendation.NotificationHighPriorityRecommendationDTO;
import com.aurus.server.notification.recommendation.NotificationHighPriorityRecommendationEvent;

import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Component;

@Component
public class NotificationEventPublisher {
    private final ApplicationEventPublisher applicationEventPublisher;

    public NotificationEventPublisher(ApplicationEventPublisher applicationEventPublisher) {
        this.applicationEventPublisher = applicationEventPublisher;
    }

    public void publishNotificationHighPriorityRecommendationEvent(
            NotificationHighPriorityRecommendationDTO notificationHighPriorityRecommendationDTO) {
        this.applicationEventPublisher.publishEvent(
                new NotificationHighPriorityRecommendationEvent(notificationHighPriorityRecommendationDTO));
    }

    public void publishNotificationHighPriorityReadingStatusEvent(
            NotificationHighPriorityReadingStatusDTO notificationHighPriorityReadingStatusDTO) {
        this.applicationEventPublisher.publishEvent(
                new NotificationHighPriorityReadingStatusEvent(notificationHighPriorityReadingStatusDTO));
    }

    public void publishNotificationHighPriorityHardwareStatusEvent(
            NotificationHighPriorityHardwareStatusDTO notificationHighPriorityHardwareStatusDTO) {
        this.applicationEventPublisher.publishEvent(
                new NotificationHighPriorityHardwareStatusEvent(notificationHighPriorityHardwareStatusDTO));
    }

    public void publishNotificationUpdateEvent() {
        this.applicationEventPublisher.publishEvent(new NotificationUpdateEvent());
    }

}
