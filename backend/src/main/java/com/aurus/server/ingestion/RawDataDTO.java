package com.aurus.server.ingestion;

import com.aurus.server.ingestion.hardware_status.HardwareStatusDTO;
import com.aurus.server.ingestion.sensor.RawSensorDataDTO;

public record RawDataDTO(RawSensorDataDTO rawSensorData, HardwareStatusDTO hardwareStatus) {

}
