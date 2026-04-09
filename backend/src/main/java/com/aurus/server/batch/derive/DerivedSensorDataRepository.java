package com.aurus.server.batch.derive;

import com.aurus.server.batch.aggregate.AggregatedSensorDataModel;

import org.springframework.data.jpa.repository.JpaRepository;

public interface DerivedSensorDataRepository extends JpaRepository<AggregatedSensorDataModel, Long> {

}
