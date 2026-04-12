package com.aurus.server.batch.aggregate.sensor;

import java.time.LocalDateTime;

public record AggregatingSensorDataEvent(LocalDateTime startingWindow, LocalDateTime endingWindow) {

}
