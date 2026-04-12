package com.aurus.server.batch.derive.weather;

import org.springframework.data.jpa.repository.JpaRepository;

public interface DerivedWeatherDataRepository extends JpaRepository<DerivedWeatherDataModel, Long> {

}
