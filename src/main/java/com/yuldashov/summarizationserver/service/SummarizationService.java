package com.yuldashov.summarizationserver.service;

import com.yuldashov.summarizationserver.dto.summarization.SummarizationRequest;
import com.yuldashov.summarizationserver.dto.summarization.SummarizationResponse;
import com.yuldashov.summarizationserver.utils.PromptBuilder;
import lombok.RequiredArgsConstructor;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class SummarizationService {
    private static final String TOPIC_NAME = "summarization-request";
    private static final String GROUP_ID = "summarization-server-group";
    private static final String TOPIC_REPLY = "summarization-reply";
    private final YandexGptService yandexGptService;
    private final PromptService promptService;

    @KafkaListener(topics = TOPIC_NAME, groupId = GROUP_ID)
    @SendTo(TOPIC_REPLY)
    public SummarizationResponse handle(SummarizationRequest request) {
        String systemPrompt = PromptBuilder.buildSystemPrompt();
        String userPrompt = promptService.buildUserPrompt(request);
        String report = yandexGptService.createRequest(systemPrompt, userPrompt);
        return new SummarizationResponse(request.requestId(), report);
    }
}
