package com.yuldashov.summarizationserver.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestClient;

@Configuration
public class RestClientConfig {
    @Value("${yandex.ai.url}")
    private String YANDEX_AI_URL;

    @Value("${yandex.api.key}")
    private String apiKey;

    @Value("${yandex.folder.id}")
    private String folderId;

    @Bean
    public RestClient restClient(RestClient.Builder builder) {
        return builder
                .baseUrl(YANDEX_AI_URL)
                .defaultHeader("Authorization", "Api-Key " + apiKey)
                .defaultHeader("OpenAI-Project", folderId)
                .build();
    }
}
