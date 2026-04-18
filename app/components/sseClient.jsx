import EventSource from "react-native-sse";


const BASE_URL = "http://192.168.18.3:8080"

export function createSSE(onMessage) {
    const es = new EventSource(`${BASE_URL}/sse/latest`);


    es.addEventListener("open", () => {
        console.log("SSE connected");
    });

    es.addEventListener("sse-realtime-data", (event) => {
        try {
            const data = JSON.parse(event.data);
            onMessage(data);
            console.log(data);
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
