package com.yuldashov.summarizationserver.service;

import com.yuldashov.summarizationserver.dto.LlmRequest;
import com.yuldashov.summarizationserver.dto.LlmResponse;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatusCode;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import java.time.Duration;
import java.util.List;
import java.util.Map;

@Service
public class YandexGptService {
    private final WebClient webClient;
    private final String model;

    public YandexGptService(WebClient webClient) {
        this.webClient = webClient;
        this.model = "gpt://b1gkjn1ok37vkdc5aevn/aliceai-llm/latest";
    }

    public String createRequest(String systemPrompt, String userPrompt) {
        LlmRequest request = new LlmRequest();
        request.setModel(model);
        request.setMessages(List.of(
                new LlmRequest.Message("system", systemPrompt),
                new LlmRequest.Message( "user", userPrompt)
        ));

        LlmResponse response = webClient.post()
                .uri("/chat/completions")
                .bodyValue(request)
                .retrieve()
                .onStatus(HttpStatusCode::isError, resp ->
                        resp.bodyToMono(String.class)
                                .map(body -> new RuntimeException("YandexGPT error: " + body)))
                .bodyToMono(LlmResponse.class)
                .block(Duration.ofSeconds(60));
        if (response==null || response.getChoices() == null) {
            throw new IllegalStateException("Пустой ответ от GPT");
        }
        String report = response.getChoices().get(0).getMessage().getContent();
        return report;
    }
}
