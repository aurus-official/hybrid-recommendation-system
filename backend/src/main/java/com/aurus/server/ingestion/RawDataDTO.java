package com.aurus.server.ingestion;

import com.aurus.server.ingestion.health_check.RawHealthCheckDataDTO;
import com.aurus.server.ingestion.sensor.RawSensorDataDTO;

public record RawDataDTO(RawSensorDataDTO rawSensorData, RawHealthCheckDataDTO rawHealthCheckData) {

}
