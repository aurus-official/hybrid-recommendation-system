package com.aurus.server.notification;

import java.util.ArrayList;
import java.util.List;

import jakarta.annotation.PostConstruct;

import org.springframework.stereotype.Component;

@Component
public class NotificationDataManager {

    private final List<NotificationDataModel> top5MostRecentNotifications = new ArrayList<>();
    private final NotificationDataRepository notificationDataRepository;

    public NotificationDataManager(NotificationDataRepository notificationDataRepository) {
        this.notificationDataRepository = notificationDataRepository;
    }

    public List<NotificationDataModel> getTop5MostRecentNotifications() {
        if (this.top5MostRecentNotifications.size() > 0) {
            return top5MostRecentNotifications;
        }
        return null;
    }

    public void updateToLatestData() {
        this.top5MostRecentNotifications.clear();
        this.top5MostRecentNotifications.addAll(notificationDataRepository.findTop5ByOrderByCreatedAtDesc());
    }

    @PostConstruct
    private void retrieveLatestData() {
        this.top5MostRecentNotifications.clear();
        this.top5MostRecentNotifications.addAll(notificationDataRepository.findTop5ByOrderByCreatedAtDesc());
    }
}
