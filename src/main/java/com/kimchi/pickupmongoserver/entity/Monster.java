package com.kimchi.pickupmongoserver.entity;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Entity
@Table(name = "monsters")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "몬스터 정보")
public class Monster {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Schema(description = "몬스터 고유 ID", example = "1")
    private Long id;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    @Schema(description = "몬스터 타입", example = "CAN")
    private MonsterType type;

    @Column(nullable = false, length = 9)
    @Schema(description = "캐릭터 이름 (9자 이하)", example = "플라몬")
    private String name;

    @ElementCollection
    @CollectionTable(name = "monster_skills", joinColumns = @JoinColumn(name = "monster_id"))
    @Column(name = "skill")
    @Schema(description = "캐릭터 스킬 목록")
    private List<String> skills;

    @Column
    @Schema(description = "생성된 이미지 URL", example = "https://storage.googleapis.com/bucket/image.jpg")
    private String imageUrl;

    @Schema(description = "몬스터 타입 열거형")
    public enum MonsterType {
        @Schema(description = "캔 몬스터") CAN,
        @Schema(description = "플라스틱 몬스터") PLASTIC,
        @Schema(description = "기타 몬스터") ETC
    }
}
