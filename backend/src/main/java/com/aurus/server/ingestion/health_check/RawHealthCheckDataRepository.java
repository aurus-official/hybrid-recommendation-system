package com.aurus.server.ingestion.health_check;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RawHealthCheckDataRepository extends JpaRepository<RawHealthCheckDataModel, Long> {
    Optional<RawHealthCheckDataModel> findFirstByOrderByIdDesc();
}
