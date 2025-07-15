package com.kimchi.pickupmongoserver.entity;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "users")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "사용자 정보")
public class User {
    
    @Id
    @Column(name = "user_id", nullable = false, unique = true)
    @Schema(description = "사용자 ID", example = "user123")
    private String userId;
    
    @Column(nullable = false)
    @Schema(description = "사용자 비밀번호", example = "password123")
    private String password;
    
    @Column(nullable = false)
    @Schema(description = "사용자 이름", example = "홍길동")
    private String name;

    @Column(nullable = false)
    @Schema(description = "사용자 경험치", example = "1500")
    private int xp;
    
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    @Schema(description = "사용자 권한", example = "USER")
    private Role role = Role.USER;
    
    @Schema(description = "사용자 권한 타입")
    public enum Role {
        @Schema(description = "일반 사용자") USER,
        @Schema(description = "관리자") ADMIN
    }
}