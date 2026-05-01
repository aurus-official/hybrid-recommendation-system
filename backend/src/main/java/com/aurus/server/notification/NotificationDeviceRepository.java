package com.aurus.server.notification;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface NotificationDeviceRepository extends JpaRepository<NotificationDeviceModel, Long> {

    @Query("SELECT nda FROM notification_device_data nda WHERE (nda.deviceId = ?1)")
    public Optional<NotificationDeviceModel> findNotificationDeviceModelByDeviceId(String deviceId);

}
