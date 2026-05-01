package com.aurus.server.notification;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import com.niamedtech.expo.exposerversdk.ExpoPushNotificationClient;
import com.niamedtech.expo.exposerversdk.request.PushNotification;
import com.niamedtech.expo.exposerversdk.response.Status;
import com.niamedtech.expo.exposerversdk.response.TicketResponse;
import com.niamedtech.expo.exposerversdk.response.TicketResponse.Ticket.Error;

import org.apache.hc.client5.http.impl.classic.CloseableHttpClient;
import org.apache.hc.client5.http.impl.classic.HttpClients;
import org.springframework.stereotype.Service;

@Service
public class NotificationService {

    private final NotificationDeviceRepository notificationDeviceRepository;
    private final NotificationDataRepository notificationDataRepository;
    private final NotificationDataToDeviceRepository notificationDataToDeviceRepository;
    private final NotificationEventPublisher notificationEventPublisher;

    public NotificationService(NotificationDeviceRepository notificationDeviceRepository,
            NotificationDataRepository notificationDataRepository,
            NotificationDataToDeviceRepository notificationDataToDeviceRepository,
            NotificationEventPublisher notificationEventPublisher) {
        this.notificationDeviceRepository = notificationDeviceRepository;
        this.notificationDataRepository = notificationDataRepository;
        this.notificationDataToDeviceRepository = notificationDataToDeviceRepository;
        this.notificationEventPublisher = notificationEventPublisher;
    }

    public void startPushNotification(NotificationCriticalDataDTO notificationCriticalDataDTO)
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
        data.put("id", String.valueOf(notificationCriticalDataDTO.id()));
        data.put("createdAt", notificationCriticalDataDTO.createdAt().toString());

        PushNotification pushNotification = new PushNotification();
        pushNotification.setTo(to);
        pushNotification.setTitle("Critical Severity Detected!");
        pushNotification.setBody("Check the status and recommendations.");
        pushNotification.setData(data);

        List<PushNotification> notifications = new ArrayList<>();
        notifications.add(pushNotification);

        List<TicketResponse.Ticket> response = client.sendPushNotifications(notifications);

        NotificationDataModel notificationDataModel = new NotificationDataModel(notificationCriticalDataDTO.id(),
                notificationCriticalDataDTO.createdAt());
        NotificationDataModel addedNotificationDataModel = notificationDataRepository.save(notificationDataModel);

        boolean isThereNewNotification = false;

        for (int i = 0; i < response.size(); ++i) {
            TicketResponse.Ticket ticket = response.get(i);
            System.out.println("ID : " + ticket.getId());
            System.out.println("Status : " + ticket.getStatus());
            // OK on success, ERROR on error
            // use import com.niamedtech.expo.exposerversdk.response.Status;

            // getDetails is only available on Error
            // System.out.println(ticket.getMessage());
            // System.out.println(ticket.getDetails().getSentAt());
            // System.out
            if (ticket.getStatus() == Status.OK) {
                NotificationDataToDeviceModel notificationDataToDeviceModel = new NotificationDataToDeviceModel(
                        notificationDeviceModels.get(i).getId(),
                        ticket.getId(), addedNotificationDataModel.getId());
                notificationDataToDeviceRepository.save(notificationDataToDeviceModel);
                continue;
            }

            if (ticket.getDetails().getError() == Error.DEVICE_NOT_REGISTERED) {
                notificationDeviceRepository.delete(notificationDeviceModels.get(i));
                continue;
            }
        }

        if (isThereNewNotification) {
            this.notificationEventPublisher.publishNotificationDataUpdateEvent();

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
