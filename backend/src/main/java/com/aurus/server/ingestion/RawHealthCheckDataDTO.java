package com.aurus.server.ingestion;

public record RawHealthCheckDataDTO(
        boolean ads1,
        boolean ads2,
        boolean bme280,
        boolean guvas12sd,
        boolean ds18b20) {

}
