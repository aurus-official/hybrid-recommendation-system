package com.aurus.server.notification;

import java.util.ArrayList;
import java.util.List;

import jakarta.annotation.PostConstruct;

import org.springframework.stereotype.Component;

@Component
public class NotificationManager {

    private final List<NotificationModel> top5MostRecentNotifications = new ArrayList<>();
    private final NotificationRepository notificationRepository;

    public NotificationManager(NotificationRepository notificationRepository) {
        this.notificationRepository = notificationRepository;
    }

    public List<NotificationModel> getTop5MostRecentNotifications() {
        if (this.top5MostRecentNotifications.size() > 0) {
            return top5MostRecentNotifications;
        }
        return null;
    }

    public void updateToLatestData() {
        this.top5MostRecentNotifications.clear();
        this.top5MostRecentNotifications.addAll(notificationRepository.findTop5ByOrderByCreatedAtDesc());
    }

    @PostConstruct
    private void retrieveLatest() {
        this.top5MostRecentNotifications.clear();
        this.top5MostRecentNotifications.addAll(notificationRepository.findTop5ByOrderByCreatedAtDesc());
    }
}
