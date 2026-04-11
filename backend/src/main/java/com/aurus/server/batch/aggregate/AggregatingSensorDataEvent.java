package com.aurus.server.batch.aggregate;

import java.time.LocalDateTime;

public record AggregatingSensorDataEvent(LocalDateTime startingWindow, LocalDateTime endingWindow) {

}
