package com.kimchi.pickupmongoserver.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "monsters")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Monster {
    @Id
    @Column(name = "monster_id", nullable = false, unique = true)
    private String id;

    @Column(nullable = false)
    private MonsterType type;

    public enum MonsterType {
        CAN,
        PLASTIC,
        ETC
    }
}
