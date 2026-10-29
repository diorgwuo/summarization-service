package com.yuldashov.summarizationserver.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.client.reactive.ReactorClientHttpConnector;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.netty.http.client.HttpClient;

import java.time.Duration;

@Configuration
public class WebClientConfig {
    private static final String YANDEX_AI_URL = "https://ai.api.cloud.yandex.net/v1/";

    @Value("${yandex.api.key}")
    private String apiKey;

    @Value("${yandex.folder.id}")
    private String folderId;

    @Bean
    public WebClient webClient() {
        HttpClient httpClient = HttpClient.create()
                .responseTimeout(Duration.ofSeconds(60));

        return WebClient.builder()
                .baseUrl(YANDEX_AI_URL)
                .defaultHeader("Authorization", "Api-Key " + apiKey)
                .defaultHeader("OpenAI-Project", folderId)
                .defaultHeader("Content-Type", "application/json")
                .clientConnector(new ReactorClientHttpConnector(httpClient))
                .build();

    }

}
