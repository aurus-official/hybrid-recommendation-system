package com.aurus.server.ingestion.weather;

public record RawWeatherDataHourlyUnitsDTO(
        String time,
        String temperature_2m,
        String relative_humidity_2m,
        String apparent_temperature,
        String precipitation,
        String precipitation_probability,
        String wind_speed_10m,
        String wind_gusts_10m,
        String vapour_pressure_deficit,
        String evapotranspiration,
        String soil_moisture_0_to_1cm,
        String soil_moisture_1_to_3cm,
        String soil_moisture_3_to_9cm) {

}
