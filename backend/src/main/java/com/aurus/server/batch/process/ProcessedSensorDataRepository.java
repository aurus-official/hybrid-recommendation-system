package com.aurus.server.batch.process;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

public interface ProcessedSensorDataRepository extends CrudRepository<ProcessedSensorDataModel, Long> {

    @Query("SELECT psd FROM processed_sensor_data psd WHERE (psd.createdAt BETWEEN ?1 AND ?2)")
    List<ProcessedSensorDataModel> findAllProcessedSensorDataModelInAWindow(LocalDateTime startingWindow,
            LocalDateTime endingWindow);
}
