package com.aurus.server.notification.health_status;

import java.io.Serializable;
import java.time.LocalDateTime;

public record NotificationHighPriorityHealthStatusDTO(LocalDateTime createdAt, long id) implements Serializable {
}
