package com.aurus.server.ingestion.weather;

import java.util.List;

public record RawWeatherDataHourlyDTO(

        List<String> time,

        List<Float> temperature_2m,
        List<Integer> relative_humidity_2m,
        List<Float> apparent_temperature,

        List<Float> precipitation,
        List<Integer> precipitation_probability,

        List<Float> wind_speed_10m,
        List<Float> wind_gusts_10m,

        List<Float> vapour_pressure_deficit,
        List<Float> evapotranspiration,

        List<Float> soil_moisture_0_to_1cm,
        List<Float> soil_moisture_1_to_3cm,
        List<Float> soil_moisture_3_to_9cm) {

}
