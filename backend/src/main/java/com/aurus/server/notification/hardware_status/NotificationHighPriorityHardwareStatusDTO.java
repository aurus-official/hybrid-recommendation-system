package com.aurus.server.notification.hardware_status;

import java.io.Serializable;
import java.time.LocalDateTime;

public record NotificationHighPriorityHardwareStatusDTO(LocalDateTime createdAt, long id) implements Serializable {
}
