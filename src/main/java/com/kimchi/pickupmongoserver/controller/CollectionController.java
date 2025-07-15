package com.kimchi.pickupmongoserver.controller;

import com.kimchi.pickupmongoserver.service.CollectionService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/collection")
@RequiredArgsConstructor
@Tag(name = "Collection", description = "사용자 도감 관련 API")
@SecurityRequirement(name = "JWT")
public class CollectionController {
    private final CollectionService service;

    @GetMapping
    @Operation(summary = "도감 조회", description = "사용자의 몬스터 도감을 조회합니다")
    @ApiResponse(responseCode = "200", description = "도감 조회 성공")
    public ResponseEntity<List<String>> getCollections() {
        return ResponseEntity.ok(service.getCollections());
    }
}
