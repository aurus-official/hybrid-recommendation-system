package com.aurus.server.ingestion.hardware_status;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface HardwareStatusRepository extends JpaRepository<HardwareStatusModel, Long> {
    Optional<HardwareStatusModel> findFirstByOrderByIdDesc();
}
