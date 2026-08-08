package com.aurus.server.notification.reading_status;

import java.io.Serializable;
import java.time.LocalDateTime;

public record NotificationHighPriorityReadingStatusDTO(LocalDateTime createdAt, long id) implements Serializable {
}
