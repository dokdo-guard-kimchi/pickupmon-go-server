package com.kimchi.pickupmongoserver.service;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Base64;

@Service
public class ImageService {

    public String imageToBase64(MultipartFile file) {
        try {
            byte[] fileBytes = file.getBytes();
            String base64Encoded = Base64.getEncoder().encodeToString(fileBytes);

            // 데이터 URL 형식으로 반환 (이미지 미리보기 가능)
            String contentType = file.getContentType(); // ex: image/png, image/jpeg
            return "data:" + contentType + ";base64," + base64Encoded;

        } catch (IOException e) {
            throw new RuntimeException("Failed to convert file to Base64", e);
        }
    }
}
