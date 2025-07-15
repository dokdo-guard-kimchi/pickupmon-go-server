package com.kimchi.pickupmongoserver.controller;

import com.kimchi.pickupmongoserver.dto.LoginRequest;
import com.kimchi.pickupmongoserver.dto.SignupRequest;
import com.kimchi.pickupmongoserver.dto.TokenResponse;
import com.kimchi.pickupmongoserver.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
@Tag(name = "Authentication", description = "사용자 인증 관련 API")
public class AuthController {

    private final UserService userService;
    
    @PostMapping("/sign-up")
    @Operation(summary = "회원가입", description = "새로운 사용자 계정을 생성합니다")
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "회원가입 성공"),
        @ApiResponse(responseCode = "400", description = "잘못된 요청")
    })
    public ResponseEntity<TokenResponse> signup(@RequestBody SignupRequest request) {
        try {
            TokenResponse response = userService.signup(request);
            return ResponseEntity.ok(response);
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(null);
        }
    }
    
    @PostMapping("/sign-in")
    @Operation(summary = "로그인", description = "기존 사용자의 로그인을 처리합니다")
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "로그인 성공"),
        @ApiResponse(responseCode = "400", description = "로그인 실패")
    })
    public ResponseEntity<TokenResponse> login(@RequestBody LoginRequest request) {
        try {
            TokenResponse response = userService.login(request);
            return ResponseEntity.ok(response);
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(null);
        }
    }
}