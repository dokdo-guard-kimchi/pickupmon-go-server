package com.kimchi.pickupmongoserver.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import com.kimchi.pickupmongoserver.entity.User;
import com.kimchi.pickupmongoserver.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/user")
@RequiredArgsConstructor
@Tag(name = "User", description = "사용자 정보 관련 API")
@SecurityRequirement(name = "JWT")
public class UserController {
    private final UserService service;

    @GetMapping("/me")
    @Operation(summary = "내 정보 조회", description = "현재 로그인된 사용자의 정보를 조회합니다")
    @ApiResponse(responseCode = "200", description = "사용자 정보 조회 성공")
    public ResponseEntity<User> me() {
        return ResponseEntity.ok(service.me());
    }

    @PostMapping("/xp")
    @Operation(summary = "XP 업데이트", description = "사용자의 경험치를 업데이트합니다")
    @ApiResponse(responseCode = "200", description = "경험치 업데이트 성공")
    public ResponseEntity<Integer> xp(@RequestParam int xp) {
        return ResponseEntity.ok(service.xp(xp));
    }
}
