package com.aurus.server.notification;

import com.aurus.server.notification.health_status.NotificationHighPriorityHealthStatusDTO;
import com.aurus.server.notification.health_status.NotificationHighPriorityHealthStatusEvent;
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

    public void publishNotificationHighPriorityHealthStatusEvent(
            NotificationHighPriorityHealthStatusDTO notificationHighPriorityHealthStatusDTO) {
        this.applicationEventPublisher.publishEvent(
                new NotificationHighPriorityHealthStatusEvent(notificationHighPriorityHealthStatusDTO));
    }

    public void publishNotificationUpdateEvent() {
        this.applicationEventPublisher.publishEvent(new NotificationUpdateEvent());
    }

}
