package com.kimchi.pickupmongoserver.entity;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

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

    @Schema(description = "몬스터 타입 열거형")
    public enum MonsterType {
        @Schema(description = "캔 몬스터") CAN,
        @Schema(description = "플라스틱 몬스터") PLASTIC,
        @Schema(description = "기타 몬스터") ETC
    }
}
