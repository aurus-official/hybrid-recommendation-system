package com.aurus.server.ingestion.weather;

import com.aurus.server.batch.BatchEventPublisher;

import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

@EnableScheduling
@Configuration
public class RawWeatherDataScheduler {

    private final RestTemplate restTemplate;
    private final RawWeatherDataRepository rawWeatherDataRepository;
    private final BatchEventPublisher batchEventPublisher;

    public RawWeatherDataScheduler(RestTemplate restTemplate, RawWeatherDataRepository rawWeatherDataRepository,
            BatchEventPublisher batchEventPublisher) {
        this.restTemplate = restTemplate;
        this.rawWeatherDataRepository = rawWeatherDataRepository;
        this.batchEventPublisher = batchEventPublisher;
    }

    @Scheduled(cron = "0 14 * * * *")
    public void getWeather() {
        String url = UriComponentsBuilder
                .fromUriString("https://api.open-meteo.com/v1/forecast")
                .queryParam("latitude", 14.3149506)
                .queryParam("longitude", 120.9109968)
                .queryParam("daily",
                        "precipitation_sum,precipitation_hours,precipitation_probability_max,temperature_2m_max,temperature_2m_min,wind_gusts_10m_max,wind_speed_10m_max,et0_fao_evapotranspiration")
                .queryParam("hourly",
                        "temperature_2m,relative_humidity_2m,apparent_temperature,precipitation,precipitation_probability,wind_gusts_10m,wind_speed_10m,vapour_pressure_deficit,evapotranspiration,soil_moisture_0_to_1cm,soil_moisture_1_to_3cm,soil_moisture_3_to_9cm")
                .queryParam("timezone", "Asia/Singapore")
                .queryParam("forecast_hours", 12)
                .toUriString();
        ResponseEntity<RawWeatherDataDTO> response = restTemplate.exchange(url,
                HttpMethod.GET, null, RawWeatherDataDTO.class);

        RawWeatherDataDTO rawWeatherDataDTO = response.getBody();
        RawWeatherDataHourlyUnitsDTO rawWeatherDataHourlyUnitsDTO = rawWeatherDataDTO.hourly_units();
        RawWeatherDataHourlyDTO rawWeatherDataHourlyDTO = rawWeatherDataDTO.hourly();
        RawWeatherDataDailyUnitsDTO rawWeatherDataDailyUnitsDTO = rawWeatherDataDTO.daily_units();
        RawWeatherDataDailyDTO rawWeatherDataDailyDTO = rawWeatherDataDTO.daily();

        System.out.println(rawWeatherDataDTO);

        RawWeatherDataHourlyUnits rawWeatherDataHourlyUnits = new RawWeatherDataHourlyUnits(
                rawWeatherDataHourlyUnitsDTO.time(),
                rawWeatherDataHourlyUnitsDTO.temperature_2m(),
                rawWeatherDataHourlyUnitsDTO.relative_humidity_2m(),
                rawWeatherDataHourlyUnitsDTO.apparent_temperature(),
                rawWeatherDataHourlyUnitsDTO.precipitation(),
                rawWeatherDataHourlyUnitsDTO.precipitation_probability(),
                rawWeatherDataHourlyUnitsDTO.wind_speed_10m(),
                rawWeatherDataHourlyUnitsDTO.wind_gusts_10m(),
                rawWeatherDataHourlyUnitsDTO.vapour_pressure_deficit(),
                rawWeatherDataHourlyUnitsDTO.evapotranspiration(),
                rawWeatherDataHourlyUnitsDTO.soil_moisture_0_to_1cm(),
                rawWeatherDataHourlyUnitsDTO.soil_moisture_1_to_3cm(),
                rawWeatherDataHourlyUnitsDTO.soil_moisture_3_to_9cm());

        RawWeatherDataHourly rawWeatherDataHourly = new RawWeatherDataHourly(
                rawWeatherDataHourlyDTO.time(),
                rawWeatherDataHourlyDTO.temperature_2m(),
                rawWeatherDataHourlyDTO.relative_humidity_2m(),
                rawWeatherDataHourlyDTO.apparent_temperature(),
                rawWeatherDataHourlyDTO.precipitation(),
                rawWeatherDataHourlyDTO.precipitation_probability(),
                rawWeatherDataHourlyDTO.wind_speed_10m(),
                rawWeatherDataHourlyDTO.wind_gusts_10m(),
                rawWeatherDataHourlyDTO.vapour_pressure_deficit(),
                rawWeatherDataHourlyDTO.evapotranspiration(),
                rawWeatherDataHourlyDTO.soil_moisture_0_to_1cm(),
                rawWeatherDataHourlyDTO.soil_moisture_1_to_3cm(),
                rawWeatherDataHourlyDTO.soil_moisture_3_to_9cm());

        RawWeatherDataDailyUnits rawWeatherDataDailyUnits = new RawWeatherDataDailyUnits(
                rawWeatherDataDailyUnitsDTO.time(),
                rawWeatherDataDailyUnitsDTO.precipitation_sum(),
                rawWeatherDataDailyUnitsDTO.precipitation_hours(),
                rawWeatherDataDailyUnitsDTO.precipitation_probability_max(),
                rawWeatherDataDailyUnitsDTO.temperature_2m_max(),
                rawWeatherDataDailyUnitsDTO.temperature_2m_min(),
                rawWeatherDataDailyUnitsDTO.wind_speed_10m_max(),
                rawWeatherDataDailyUnitsDTO.wind_gusts_10m_max(),
                rawWeatherDataDailyUnitsDTO.et0_fao_evapotranspiration());

        RawWeatherDataDaily rawWeatherDataDaily = new RawWeatherDataDaily(
                rawWeatherDataDailyDTO.time(),
                rawWeatherDataDailyDTO.precipitation_sum(),
                rawWeatherDataDailyDTO.precipitation_hours(),
                rawWeatherDataDailyDTO.precipitation_probability_max(),
                rawWeatherDataDailyDTO.temperature_2m_max(),
                rawWeatherDataDailyDTO.temperature_2m_min(),
                rawWeatherDataDailyDTO.wind_speed_10m_max(),
                rawWeatherDataDailyDTO.wind_gusts_10m_max(),
                rawWeatherDataDailyDTO.et0_fao_evapotranspiration());

        RawWeatherDataModel rawWeatherDataModel = new RawWeatherDataModel(
                rawWeatherDataDTO.latitude(),
                rawWeatherDataDTO.longitude(),
                rawWeatherDataDTO.generationtime_ms(),
                rawWeatherDataDTO.utc_offset_seconds(),
                rawWeatherDataDTO.timezone(),
                rawWeatherDataDTO.timezone_abbreviation(),
                rawWeatherDataDTO.elevation(),
                rawWeatherDataHourlyUnits,
                rawWeatherDataHourly,
                rawWeatherDataDailyUnits,
                rawWeatherDataDaily);

        RawWeatherDataModel returnedRawWeatherDataModel = rawWeatherDataRepository.save(rawWeatherDataModel);
        batchEventPublisher.publishProcessingWeatherDataEvent(returnedRawWeatherDataModel.getId());
    }

}
