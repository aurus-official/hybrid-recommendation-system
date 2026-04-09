package com.aurus.server.batch.aggregate;

import java.time.LocalDateTime;

public record AggregatingEvent(LocalDateTime startingWindow, LocalDateTime endingWindow) {

}
