import EventSource from "react-native-sse";

const BASE_URL = "http://192.168.18.3:8080";

export function createSSE(
    setSSEData,
    setDataSource,
    setNotificationData,
    expoPushToken,
    deviceId
) {
    console.log("PUSH TOKEN SSE : " + expoPushToken);
    const es = new EventSource(`${BASE_URL}/sse/latest`, {
        headers: {
            "X-Expo-Push-Token": `${expoPushToken}`,
            "X-Device-Id": `${deviceId}`,
        },
    });

    es.addEventListener("open", () => {
        console.log("SSE connected");
        setDataSource("Latest");
    });

    es.addEventListener("all-realtime-data", (event) => {
        try {
            const data = JSON.parse(event.data);
            setSSEData(data);
        } catch (err) {
            console.error("SSE parse error:", err);
        }
    });

    es.addEventListener("top-5-most-notifications", (event) => {
        try {
            const data = JSON.parse(event.data);
            setNotificationData(data);
        } catch (err) {
            console.error("SSE parse error:", err);
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
