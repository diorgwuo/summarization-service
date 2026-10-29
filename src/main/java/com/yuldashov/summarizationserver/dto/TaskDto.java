package com.yuldashov.summarizationserver.dto;

import java.time.LocalDateTime;

public record TaskDto(
        Long id,
        String title,
        String description,
        String status,
        LocalDateTime completedAt) {}