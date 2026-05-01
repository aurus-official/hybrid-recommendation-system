package com.aurus.server.notification;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface NotificationDataToDeviceRepository extends CrudRepository<NotificationDataToDeviceModel, Long> {
}
