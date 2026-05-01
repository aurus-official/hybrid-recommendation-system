package com.aurus.server.llm;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.time.Duration;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;

import org.springframework.stereotype.Service;

@Service
public class LLMGenerator {

    private final HttpClient httpClient = HttpClient.newHttpClient();

    public String generateRecommendation(String prompt) {
        System.out.println(prompt);
        try {
            ObjectMapper mapper = new ObjectMapper();

            ObjectNode body = mapper.createObjectNode();
            body.put("model", "qwen2.5:7b");
            body.put("stream", false);
            body.put("prompt", prompt);

            String requestBody = mapper.writeValueAsString(body);

            HttpRequest request = HttpRequest.newBuilder()
                    .uri(URI.create("http://localhost:11434/api/generate"))
                    .timeout(Duration.ofMinutes(5))
                    .header("Content-Type", "application/json")
                    .POST(HttpRequest.BodyPublishers.ofString(requestBody))
                    .build();

            HttpResponse<String> response = httpClient.send(
                    request,
                    HttpResponse.BodyHandlers.ofString());
            System.out.println(requestBody);

            return extractResponse(response.body());
        } catch (Exception e) {
            throw new RuntimeException("Ollama call failed", e);
        }
    }

    private String extractResponse(String json) {
        int start = json.indexOf("\"response\":\"") + 12;
        int end = json.indexOf("\"", start);
        return json.substring(start, end).replace("\\n", "\n");
    }
}
