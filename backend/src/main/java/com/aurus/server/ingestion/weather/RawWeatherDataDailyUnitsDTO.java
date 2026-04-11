package com.aurus.server.ingestion.weather;

public record RawWeatherDataDailyUnitsDTO(
        String time,
        String precipitation_sum,
        String precipitation_hours,
        String precipitation_probability_max,
        String temperature_2m_max,
        String temperature_2m_min,
        String wind_speed_10m_max,
        String wind_gusts_10m_max,
        String et0_fao_evapotranspiration) {

}
