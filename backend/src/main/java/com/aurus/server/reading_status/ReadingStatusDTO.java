package com.aurus.server.reading_status;

import java.time.LocalDateTime;

public record ReadingStatusDTO(
        long id,
        boolean isValidSoilTemp,
        boolean isValidAirTemp,
        boolean isValidHumidity,
        boolean isValidPressure,
        boolean isValidLux,
        boolean isValidUvVolts,
        boolean isValidTdsVolts,
        boolean isValidProngMoisture,
        boolean isValidCapacitiveMoisture,
        LocalDateTime createdAt) {

}
