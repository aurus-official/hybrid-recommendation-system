import React, { createContext, useContext, useEffect, useState } from "react";
import * as Notifications from "expo-notifications";
import { registerForPushNotificationsAsync } from "../utils/registerForPushNotifications";
import * as Application from 'expo-application';

const NotificationContext = createContext(null);

export function NotificationProvider({ children }) {
    const [expoPushToken, setExpoPushToken] = useState("");
    const [deviceId, setDeviceId] = useState("");
    const [notification, setNotification] = useState(undefined);

    useEffect(() => {
        setDeviceId(Application.getAndroidId);

        registerForPushNotificationsAsync()
            .then((token) => setExpoPushToken(token ?? ""))
            .catch((error) => setExpoPushToken(`${error}`));

        const notificationListener =
            Notifications.addNotificationReceivedListener((notification) => {
                setNotification(notification);
            });

        const responseListener =
            Notifications.addNotificationResponseReceivedListener(
                (response) => {
                    console.log(response);
                }
            );

        return () => {
            notificationListener.remove();
            responseListener.remove();
        };
    }, []);


    return (
        <NotificationContext.Provider value={{ expoPushToken, deviceId, notification }}>
            {children}
        </NotificationContext.Provider>
    );
}

export function useNotificationData() {
    return useContext(NotificationContext);
}
