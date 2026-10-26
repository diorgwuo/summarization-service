package com.yuldashov.summarizationserver.dto.llm;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Setter
@Getter
public class LlmResponse {
    private List<Choice> choices;

    @Setter
    @Getter
    public static class Choice {
        private Message message;

    }

    @Setter
    @Getter
    public static class Message {
        private String content;

    }
}
