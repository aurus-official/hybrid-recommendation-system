import * as Notifications from "expo-notifications";
import * as Application from "expo-application";
import { useStore } from "../store/useStore";
import { useEffect } from "react";

export function useNotificationManager(queryClient) {
    const setExpoPushToken = useStore((state) => state.setExpoPushToken);
    const setDeviceId = useStore((state) => state.setDeviceId);

    useEffect(() => {
        const notificationListener =
            Notifications.addNotificationReceivedListener((notification) => {});

        const responseListener =
            Notifications.addNotificationResponseReceivedListener(
                (response) => {}
            );

        return () => {
            notificationListener.remove();
            responseListener.remove();
        };
    }, []);

    const initNotifications = async () => {
        const { status: existingStatus } =
            await Notifications.getPermissionsAsync();
        let finalStatus = existingStatus;

        if (existingStatus !== "granted") {
            const { status } = await Notifications.requestPermissionsAsync();
            finalStatus = status;
        }

        if (finalStatus !== "granted") return;

        const token = (await Notifications.getExpoPushTokenAsync()).data;
        const id = Application.getAndroidId();

        setExpoPushToken(token);
        setDeviceId(id);
    };

    return { initNotifications };
}
