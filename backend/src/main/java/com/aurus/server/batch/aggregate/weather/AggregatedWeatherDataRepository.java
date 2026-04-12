package com.aurus.server.batch.aggregate.weather;

import org.springframework.data.jpa.repository.JpaRepository;

public interface AggregatedWeatherDataRepository extends JpaRepository<AggregatedWeatherDataModel, Long> {

}
