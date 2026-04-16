import EventSource from "react-native-sse";


const BASE_URL = "http://localhost:8080"

export function createSSE(onMessage) {
    const es = new EventSource(`${BASE_URL}/sse/latest`);

    es.addEventListener("open", () => {
        console.log("SSE connected");
    });

    es.addEventListener("sse-latest-data", (event) => {
        try {
            const data = JSON.parse(event.data);
            onMessage(data);
        } catch (err) {
            console.error("SSE parse error:", err);
        }
    });

    es.addEventListener("error", (err) => {
        console.log("SSE error:", err);
    });

    return es;
}
