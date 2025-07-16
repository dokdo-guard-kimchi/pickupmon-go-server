package com.kimchi.pickupmongoserver.config;

import com.google.auth.oauth2.GoogleCredentials;
import com.google.cloud.storage.Storage;
import com.google.cloud.storage.StorageOptions;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.io.FileInputStream;
import java.io.IOException;

@Configuration
@Slf4j
public class GcsConfig {

    @Value("${gcp.storage.credentials-file-path:}")
    private String credentialsFilePath;

    @Bean
    public Storage storage() {
        try {
            StorageOptions.Builder builder = StorageOptions.newBuilder();
            
            if (credentialsFilePath != null && !credentialsFilePath.isEmpty()) {
                GoogleCredentials credentials = GoogleCredentials.fromStream(
                    new FileInputStream(credentialsFilePath));
                builder.setCredentials(credentials);
                log.info("GCP Storage 서비스 계정 인증 파일 사용: {}", credentialsFilePath);
            } else {
                log.info("GCP Storage 기본 인증 사용 (Application Default Credentials)");
            }
            
            return builder.build().getService();
        } catch (IOException e) {
            log.error("GCP Storage 초기화 실패", e);
            throw new RuntimeException("Failed to initialize GCP Storage", e);
        }
    }
}