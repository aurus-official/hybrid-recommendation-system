package com.aurus.server.ingestion;

import org.springframework.data.repository.CrudRepository;

public interface RawHealthCheckDataRepository extends CrudRepository<RawHealthCheckDataModel, Long> {

}
