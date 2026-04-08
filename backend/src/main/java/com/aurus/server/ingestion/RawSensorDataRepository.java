package com.aurus.server.ingestion;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface RawSensorDataRepository extends JpaRepository<RawSensorDataModel, Long> {
    @Query("SELECT rsd FROM raw_sensor_data rsd WHERE (rsd.id < ?1) ORDER BY rsd.id DESC LIMIT 2")
    List<RawSensorDataModel> findTwoPastRawSensorDataModels(long id);

}
