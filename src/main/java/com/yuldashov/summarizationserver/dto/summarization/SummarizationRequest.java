package com.yuldashov.summarizationserver.dto.summarization;

import com.yuldashov.summarizationserver.dto.TaskDto;

import java.util.List;

public record SummarizationRequest(
        String requestId,
        Long userId,
        String username,
        String email,
        List<TaskDto> doneTasks,
        List<TaskDto> undoneTasks) {
}

