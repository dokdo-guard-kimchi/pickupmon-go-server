package com.kimchi.pickupmongoserver.entity;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "collections")
@Data
@NoArgsConstructor
@Builder
@AllArgsConstructor
@Schema(description = "사용자 컴렉션 정보")
public class Collection {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Schema(description = "컴렉션 고유 ID", example = "1")
    private Long id;

    @ManyToOne
    @JoinColumn(name = "user_id")
    @Schema(description = "컴렉션 소유자")
    private User user;

    @Column(nullable = false)
    @Schema(description = "몬스터 이미지 URL", example = "https://example.com/monster.jpg")
    private String imageUrl;
}
