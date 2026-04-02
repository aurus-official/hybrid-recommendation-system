package com.aurus.server.ingestion;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RawSensorDataRepository extends CrudRepository<RawSensorDataModel, Long> {

}
