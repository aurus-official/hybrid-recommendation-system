package com.aurus.server.notification;

import java.time.LocalDateTime;

public record NotificationCriticalDataDTO(LocalDateTime createdAt, long id) {
}
