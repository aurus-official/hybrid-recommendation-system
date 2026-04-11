package com.aurus.server.ingestion.weather;

public record RawWeatherDataDTO(

        float latitude,
        float longitude,
        float generationtime_ms,
        int utc_offset_seconds,
        String timezone,
        String timezone_abbreviation,
        float elevation,

        RawWeatherDataHourlyUnitsDTO hourly_units,
        RawWeatherDataHourlyDTO hourly,

        RawWeatherDataDailyUnitsDTO daily_units,
        RawWeatherDataDailyDTO daily

) {

}
