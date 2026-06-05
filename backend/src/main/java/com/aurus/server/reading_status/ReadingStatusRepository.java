package com.aurus.server.reading_status;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ReadingStatusRepository extends JpaRepository<ReadingStatusModel, Long> {
    Optional<ReadingStatusModel> findFirstByOrderByIdDesc();

}
