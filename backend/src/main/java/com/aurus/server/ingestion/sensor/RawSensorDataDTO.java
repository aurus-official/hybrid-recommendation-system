package com.aurus.server.ingestion.sensor;

public record RawSensorDataDTO(
        float soilTemp,
        float airTemp,
        float humidity,
        float pressure,
        float lux,
        float uvVolts,
        float tdsVolts,
        float prongMoisture,
        float capacitiveMoisture) {
}
