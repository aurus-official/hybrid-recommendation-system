package com.aurus.server.ingestion;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RawSensorDataRepository extends JpaRepository<RawSensorDataModel, Long> {
    RawSensorDataModel findFirstByOrderByCreatedAtDesc();
}
