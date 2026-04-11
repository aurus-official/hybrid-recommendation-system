package com.aurus.server.ingestion.health_check;

import org.springframework.data.repository.CrudRepository;

public interface RawHealthCheckDataRepository extends CrudRepository<RawHealthCheckDataModel, Long> {

}
