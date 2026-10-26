package com.yuldashov.summarizationserver.service;

import com.yuldashov.summarizationserver.dto.llm.LlmRequest;
import com.yuldashov.summarizationserver.dto.llm.LlmResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatusCode;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import java.time.Duration;
import java.util.List;

@Service
@RequiredArgsConstructor
public class YandexGptService {
    private final WebClient webClient;
    @Value("${yandex.model.ai}")
    private String model;

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
        return response.getChoices().get(0).getMessage().getContent();
    }
}
