package com.yuldashov.summarizationserver.service;

import com.yuldashov.summarizationserver.dto.llm.LlmRequest;
import com.yuldashov.summarizationserver.dto.llm.LlmResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClient;

import java.util.List;

@Service
@RequiredArgsConstructor
public class YandexGptService {
    private final RestClient restClient;
    @Value("${yandex.model.ai}")
    private String model;

    public String createRequest(String systemPrompt, String userPrompt) {
        LlmRequest request = new LlmRequest();
        request.setModel(model);
        request.setMessages(List.of(
                new LlmRequest.Message("system", systemPrompt),
                new LlmRequest.Message("user", userPrompt)
        ));

        LlmResponse response = restClient.post()
                .uri("/chat/completions")
                .body(request)
                .retrieve()
                .body(LlmResponse.class);

        if (response == null || response.getChoices() == null) {
            throw new IllegalStateException("Пустой ответ от GPT");
        }
        return response.getChoices().get(0).getMessage().getContent();
    }
}
