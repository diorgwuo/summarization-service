package com.yuldashov.summarizationserver.dto.llm;

import lombok.*;

import java.util.List;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class LlmRequest {
    String model;
    List<Message> messages;

    @Setter
    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class Message {
        private String role;
        private String content;
    }
}
