import EventSource from "react-native-sse";
import { useStore } from "../store/useStore";

const BASE_URL = "https://armanda-uncontracted-jasper.ngrok-free.dev";

export function startSSE(expoPushToken, deviceId) {
    const es = new EventSource(`${BASE_URL}/sse/latest`, {
        headers: {
            "X-Expo-Push-Token": `${expoPushToken}`,
            "X-Device-Id": `${deviceId}`,
            "ngrok-skip-browser-warning": "true",
        },
        reconnectInterval: 10000,
    });

    es.addEventListener("open", () => {
        console.log("SSE connected");
    });

    es.addEventListener("all-realtime-data", (event) => {
        try {
            const cleanedData = event.data.replace(/[\u0000-\u001F]/g, "");
            const data = JSON.parse(cleanedData);
            useStore.getState().setSSEUpdate(data);
        } catch (err) {
            console.error("SSE Sensor parse error:", err);
        }
    });

    es.addEventListener("top-5-most-notifications", (event) => {
        try {
            const data = JSON.parse(event.data);
            if (data != null) {
                useStore.getState().setNotifications(data);
                useStore.getState().setBadgeCount();
            }
        } catch (err) {
            console.error("SSE Notif parse error:", err);
        }
    });

    es.addEventListener("error", (err) => {
        console.log("SSE error:", err);

        es.close();

        setTimeout(() => {
            es.open();
        }, 10000);
    });

    return es;
}
