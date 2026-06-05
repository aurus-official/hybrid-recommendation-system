package com.aurus.server.ingestion.hardware_status;

public record HardwareStatusDTO(
        boolean ads1,
        boolean ads2,
        boolean bme280,
        boolean guvas12sd,
        boolean ds18b20) {

}
