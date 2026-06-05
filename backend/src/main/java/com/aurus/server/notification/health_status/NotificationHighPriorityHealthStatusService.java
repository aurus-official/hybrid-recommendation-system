package com.aurus.server.notification.health_status;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import com.aurus.server.notification.NotificationEventPublisher;
import com.aurus.server.notification.NotificationModel;
import com.aurus.server.notification.NotificationRepository;
import com.aurus.server.notification.device.NotificationDeviceModel;
import com.aurus.server.notification.device.NotificationDeviceRepository;
import com.niamedtech.expo.exposerversdk.ExpoPushNotificationClient;
import com.niamedtech.expo.exposerversdk.request.PushNotification;
import com.niamedtech.expo.exposerversdk.response.Status;
import com.niamedtech.expo.exposerversdk.response.TicketResponse;
import com.niamedtech.expo.exposerversdk.response.TicketResponse.Ticket.Error;

import org.apache.hc.client5.http.impl.classic.CloseableHttpClient;
import org.apache.hc.client5.http.impl.classic.HttpClients;
import org.springframework.stereotype.Service;

@Service
public class NotificationHighPriorityHealthStatusService {

    private final NotificationDeviceRepository notificationDeviceRepository;
    private final NotificationRepository notificationRepository;
    private final NotificationEventPublisher notificationEventPublisher;

    public NotificationHighPriorityHealthStatusService(NotificationDeviceRepository notificationDeviceRepository,
            NotificationRepository notificationRepository,
            NotificationEventPublisher notificationEventPublisher) {
        this.notificationDeviceRepository = notificationDeviceRepository;
        this.notificationRepository = notificationRepository;
        this.notificationEventPublisher = notificationEventPublisher;
    }

    public void startPushNotification(
            NotificationHighPriorityHealthStatusDTO notificationHighPriorityHealthStatusDTO)
            throws IOException {

        List<NotificationDeviceModel> notificationDeviceModels = notificationDeviceRepository.findAll();
        if (notificationDeviceModels.size() == 0) {
            return;
        }

        List<String> to = notificationDeviceModels.stream().map(item -> item.getExpoPushToken())
                .toList();

        CloseableHttpClient httpClient = HttpClients.createDefault();
        ExpoPushNotificationClient client = ExpoPushNotificationClient
                .builder()
                .setHttpClient(httpClient)
                .build();

        Map<String, Object> data = new HashMap<>();
        data.put("id", String.valueOf(notificationHighPriorityHealthStatusDTO.id()));
        data.put("createdAt", notificationHighPriorityHealthStatusDTO.createdAt().toString());

        PushNotification pushNotification = new PushNotification();
        pushNotification.setTo(to);
        pushNotification.setTitle("Invalid Sensor Value In A Timeframe Detected");
        pushNotification.setBody("Check the system health logs.");
        pushNotification.setData(data);

        List<PushNotification> notifications = new ArrayList<>();
        notifications.add(pushNotification);

        List<TicketResponse.Ticket> response = client.sendPushNotifications(notifications);

        NotificationModel notificationDataModel = new NotificationModel(notificationHighPriorityHealthStatusDTO.id(),
                notificationHighPriorityHealthStatusDTO.createdAt(),
                com.aurus.server.notification.NotificationType.SYSTEM_HEALTH_ISSUE);
        notificationRepository.save(notificationDataModel);

        boolean isThereNewPushNotification = false;

        for (int i = 0; i < response.size(); ++i) {
            TicketResponse.Ticket ticket = response.get(i);
            // OK on success, ERROR on error
            // use import com.niamedtech.expo.exposerversdk.response.Status;

            // getDetails is only available on Error
            // System.out.println(ticket.getMessage());
            // System.out.println(ticket.getDetails().getSentAt());
            // System.out
            if (ticket.getStatus() == Status.OK) {
                isThereNewPushNotification = true;
                continue;
            }

            if (ticket.getDetails().getError() == Error.DEVICE_NOT_REGISTERED) {
                notificationDeviceRepository.delete(notificationDeviceModels.get(i));
                continue;
            }
        }

        if (isThereNewPushNotification) {
            this.notificationEventPublisher.publishNotificationUpdateEvent();

        }
    }

    public void addDeviceToNotificationService(String expoPushToken, String deviceId) {
        Optional<NotificationDeviceModel> existingNotificationDeviceModel = notificationDeviceRepository
                .findNotificationDeviceModelByDeviceId(deviceId);

        if (existingNotificationDeviceModel.isPresent()) {
            existingNotificationDeviceModel.get().setExpoPushToken(expoPushToken);
            notificationDeviceRepository.save(existingNotificationDeviceModel.get());
            return;
        }

        NotificationDeviceModel newNotificationDeviceModel = new NotificationDeviceModel(deviceId, expoPushToken);
        notificationDeviceRepository.save(newNotificationDeviceModel);
    }
}
