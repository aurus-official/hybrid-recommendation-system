package com.aurus.server.batch.aggregate.sensor;

import org.springframework.data.jpa.repository.JpaRepository;

public interface AggregatedSensorDataRepository extends JpaRepository<AggregatedSensorDataModel, Long> {

}
