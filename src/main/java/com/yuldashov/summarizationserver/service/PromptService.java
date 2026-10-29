package com.yuldashov.summarizationserver.service;

import com.yuldashov.summarizationserver.dto.summarization.SummarizationRequest;
import com.yuldashov.summarizationserver.dto.TaskDto;
import org.springframework.stereotype.Service;

@Service
public class PromptService {
    public String buildUserPrompt(SummarizationRequest request) {
        StringBuilder sb = new StringBuilder();
        sb.append("Пользователь: " ).append(request.username()).append("\n\n");
        sb.append("Сделано за сутки = ").append(request.doneTasks().size()).append(":\n");
        for (TaskDto task : request.doneTasks()) {
            sb.append("- ").append(task.title()).append("\n");
        }
        sb.append("Не сделано = ").append(request.undoneTasks().size()).append(":\n");
        for (TaskDto task : request.undoneTasks()) {
            sb.append("- ").append(task.title()).append("\n");
        }
        return sb.toString();
    }
}
