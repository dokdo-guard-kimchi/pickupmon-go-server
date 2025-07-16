package com.kimchi.pickupmongoserver.config;

import com.theokanning.openai.service.OpenAiService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.time.Duration;

@Configuration
@Slf4j
public class OpenAiConfig {

    @Value("${openai.api-key}")
    private String openaiApiKey;

    @Bean
    public OpenAiService openAiService() {
        log.info("OpenAI Service 초기화");
        return new OpenAiService(openaiApiKey, Duration.ofSeconds(60));
    }
}