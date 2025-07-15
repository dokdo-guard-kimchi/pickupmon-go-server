package com.kimchi.pickupmongoserver.controller;

import com.kimchi.pickupmongoserver.entity.Monster;
import com.kimchi.pickupmongoserver.service.MonsterService;
import com.kimchi.pickupmongoserver.service.PhotoService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.Parameter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/monster")
@RequiredArgsConstructor
@Tag(name = "Monster", description = "몬스터 관련 API")
@SecurityRequirement(name = "JWT")
public class MonsterController {
    private final MonsterService service;
    private final PhotoService photoService;

    @GetMapping("/find")
    @Operation(summary = "몬스터 찾기", description = "특정 타입의 몬스터를 찾습니다")
    @ApiResponse(responseCode = "200", description = "몬스터 찾기 성공")
    public ResponseEntity<Monster> findMonster(@Parameter(description = "몬스터 타입") @RequestParam Monster.MonsterType type) {
        return ResponseEntity.ok(service.findMonster(type));
    }

    @PostMapping("/catch")
    @Operation(summary = "몬스터 포획", description = "사진을 업로드하여 몬스터를 포획하고 도감에 추가합니다")
    @ApiResponse(responseCode = "200", description = "몬스터 포획 성공")
    public ResponseEntity<String> catchMonster(@Parameter(description = "몬스터 사진 파일") @RequestParam("file") MultipartFile file) {
        String url = photoService.photoToUrl(file);
        service.catchMonster(url);
        return ResponseEntity.ok("몬스터가 도감에 추가되었습니다.");
    }
}
