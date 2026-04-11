package com.aurus.server.ingestion.weather;

import java.util.List;

public record RawWeatherDataDailyDTO(

        List<String> time,

        List<Float> precipitation_sum,
        List<Float> precipitation_hours,
        List<Integer> precipitation_probability_max,

        List<Float> temperature_2m_max,
        List<Float> temperature_2m_min,

        List<Float> wind_speed_10m_max,
        List<Float> wind_gusts_10m_max,

        List<Float> et0_fao_evapotranspiration) {

}
