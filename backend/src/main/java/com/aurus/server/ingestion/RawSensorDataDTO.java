package com.aurus.server.ingestion;

public record RawSensorDataDTO(
        float waterTemp,
        float airTemp,
        float humidity,
        float pressure,
        float lux,
        float uvVolts,
        float tdsVolts,
        float prongMoisture,
        float capacitiveMoisture) {

    // @Override
    // public String toString() {
    // return String.format(
    // "{ water_temp : %f,\nair_temp : %f,\nhumidity : %f,\npressure : %f,\nlux :
    // %f,\nuv_volts : %f,\ntds_volts : %f,\nprong_moist : %f,\ncapacitive_moist :
    // %f }",
    // water_temp(), air_temp(), humidity(), pressure(), uv_volts(), tds_volts(),
    // prong_moist(),
    // capacitive_moist());
    // }
}
