package com.kimchi.pickupmongoserver.service;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
@Slf4j
public class CharacterGenerationService {

    @Value("${openai.api-key}")
    private String openaiApiKey;
    
    private final WebClient.Builder webClientBuilder;

    @Data
    public static class CharacterInfo {
        @JsonProperty("waste_type")
        private String wasteType;
        
        @JsonProperty("character_name")
        private String characterName;
        
        @JsonProperty("skills")
        private List<String> skills;
    }

    public CharacterInfo generateCharacterFromTrashImage(String imageUrl) {
        try {
            WebClient webClient = webClientBuilder
                    .baseUrl("https://api.openai.com")
                    .defaultHeader(HttpHeaders.AUTHORIZATION, "Bearer " + openaiApiKey)
                    .defaultHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
                    .build();

            Map<String, Object> requestBody = createVisionRequestBody(imageUrl);
            
            String response = webClient.post()
                    .uri("/v1/chat/completions")
                    .bodyValue(requestBody)
                    .retrieve()
                    .bodyToMono(String.class)
                    .block();

            log.info("OpenAI 응답: {}", response);
            
            return parseOpenAiResponse(response);
        } catch (Exception e) {
            log.error("캐릭터 생성 실패: {}", e.getMessage());
            throw new RuntimeException("Character generation failed", e);
        }
    }

    private Map<String, Object> createVisionRequestBody(String imageUrl) {
        String systemPrompt = createPrompt();
        
        return Map.of(
            "model", "gpt-4o",
            "messages", List.of(
                Map.of(
                    "role", "system",
                    "content", systemPrompt
                ),
                Map.of(
                    "role", "user", 
                    "content", List.of(
                        Map.of("type", "text", "text", "이 이미지에 있는 쓰레기를 분석해서 JSON 형식으로 답변해주세요."),
                        Map.of("type", "image_url", "image_url", Map.of("url", imageUrl))
                    )
                )
            ),
            "max_tokens", 500
        );
    }

    private CharacterInfo parseOpenAiResponse(String response) {
        try {
            ObjectMapper mapper = new ObjectMapper();
            JsonNode rootNode = mapper.readTree(response);
            String content = rootNode.path("choices").get(0).path("message").path("content").asText();
            
            return parseCharacterInfo(content);
        } catch (Exception e) {
            log.error("OpenAI 응답 파싱 실패: {}", e.getMessage());
            throw new RuntimeException("Failed to parse OpenAI response", e);
        }
    }

    private String createPrompt() {
        return """
            당신은 환경 보호를 위한 게임의 캐릭터 생성 AI입니다.
            
            업로드된 이미지의 쓰레기를 분석하고, 다음 규칙에 따라 캐릭터를 생성해주세요:
            
            1. 쓰레기 종류를 정확히 판단하세요 (예: 플라스틱 병, 종이 상자, 캔, 비닐봉지 등)
            2. 쓰레기 종류에 어울리는 포켓몬 스타일의 캐릭터 이름을 만드세요 (9자 이하, 한글)
            3. 그 캐릭터가 가질 수 있는 스킬 4개를 창작하세요 (쓰레기 종류와 연관되어야 함)
            
            응답은 반드시 다음 JSON 형식으로만 답변하세요:
            {
                "waste_type": "구체적인 쓰레기 종류",
                "character_name": "9자 이하 캐릭터 이름",
                "skills": ["스킬1", "스킬2", "스킬3", "스킬4"]
            }
            
            다른 설명이나 텍스트 없이 JSON만 반환하세요.
            """;
    }

    private CharacterInfo parseCharacterInfo(String response) {
        try {
            ObjectMapper mapper = new ObjectMapper();
            String cleanResponse = response.trim();
            if (cleanResponse.startsWith("```json")) {
                cleanResponse = cleanResponse.substring(7);
            }
            if (cleanResponse.endsWith("```")) {
                cleanResponse = cleanResponse.substring(0, cleanResponse.length() - 3);
            }
            cleanResponse = cleanResponse.trim();
            
            return mapper.readValue(cleanResponse, CharacterInfo.class);
        } catch (JsonProcessingException e) {
            log.error("JSON 파싱 실패: {}", e.getMessage());
            throw new RuntimeException("Failed to parse character info", e);
        }
    }
}