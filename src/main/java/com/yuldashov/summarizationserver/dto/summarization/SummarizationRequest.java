package com.yuldashov.summarizationserver.dto.summarization;

import com.yuldashov.summarizationserver.dto.Task;

import java.util.List;

public record SummarizationRequest(
        String requestId,
        Long userId,
        String username,
        List<Task> doneTasks,
        List<Task> undoneTasks) {
}

