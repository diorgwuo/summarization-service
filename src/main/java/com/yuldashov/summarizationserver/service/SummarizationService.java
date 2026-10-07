package com.yuldashov.summarizationserver.service;

import com.yuldashov.summarizationserver.dto.SummarizationRequest;
import com.yuldashov.summarizationserver.dto.SummarizationResponse;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Service;

@Service
public class SummarizationService {
    private final YandexGptService yandexGptService;
    private final PromptService promptService;

    public SummarizationService(YandexGptService yandexGptService, PromptService promptService) {
        this.yandexGptService = yandexGptService;
        this.promptService = promptService;
    }

    @KafkaListener(topics = "summarization-request", groupId = "summarization-server-group")
    @SendTo("summarization-reply")
    public SummarizationResponse handle(SummarizationRequest request) {
        String systemPrompt = promptService.buildSystemPrompt();
        String userPrompt = promptService.buildUserPrompt(request);

        String report = yandexGptService.createRequest(systemPrompt, userPrompt);

        return new SummarizationResponse(request.getRequestId(), report);
    }
}
