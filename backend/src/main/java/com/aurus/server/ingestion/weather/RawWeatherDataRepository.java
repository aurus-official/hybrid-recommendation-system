package com.aurus.server.ingestion.weather;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RawWeatherDataRepository extends JpaRepository<RawWeatherDataModel, Long> {

}
