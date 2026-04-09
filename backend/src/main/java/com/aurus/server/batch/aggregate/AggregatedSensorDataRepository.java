package com.aurus.server.batch.aggregate;

import org.springframework.data.jpa.repository.JpaRepository;

public interface AggregatedSensorDataRepository extends JpaRepository<AggregatedSensorDataModel, Long> {

}
