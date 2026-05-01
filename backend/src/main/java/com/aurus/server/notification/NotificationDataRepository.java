package com.aurus.server.notification;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface NotificationDataRepository extends JpaRepository<NotificationDataModel, Long> {
    List<NotificationDataModel> findTop5ByOrderByCreatedAtDesc();
}
