package com.aurus.server.batch.process.weather;

import org.springframework.data.repository.CrudRepository;

public interface ProcessedWeatherDataRepository extends CrudRepository<ProcessedWeatherDataModel, Long> {
}
