package com.yuldashov.summarizationserver.service;

import com.yuldashov.summarizationserver.dto.SummarizationRequest;
import com.yuldashov.summarizationserver.dto.TaskDto;
import org.springframework.stereotype.Service;

@Service
public class PromptService {

    public String buildSystemPrompt() {
        return "Ты — ассистент планировщика задач. Составь краткий отчёт по итогам дня. " +
                "Опиши, что сделано, что осталось. Пиши по-русски и кратко.";
    }

    public String buildUserPrompt(SummarizationRequest request) {
        StringBuilder sb = new StringBuilder();
        sb.append("Пользователь: " ).append(request.getUsername()).append("\n\n");
        sb.append("Сделано за сутки = ").append(request.getDoneTasks().size()).append(":\n");
        for (TaskDto task : request.getDoneTasks()) {
            sb.append("- ").append(task.getTitle()).append("\n");
        }
        sb.append("Не сделано = ").append(request.getUndoneTasks().size()).append(":\n");
        for (TaskDto task : request.getUndoneTasks()) {
            sb.append("- ").append(task.getTitle()).append("\n");
        }

        return sb.toString();
    }
}
