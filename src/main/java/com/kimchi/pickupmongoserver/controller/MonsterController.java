package com.kimchi.pickupmongoserver.controller;

import com.kimchi.pickupmongoserver.entity.Monster;
import com.kimchi.pickupmongoserver.service.MonsterService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/monster")
@RequiredArgsConstructor
public class MonsterController {
    private final MonsterService service;

    @GetMapping("/find")
    public ResponseEntity<Monster> findMonster(@RequestParam Monster.MonsterType type) {
        return ResponseEntity.ok(service.findMonster(type));
    }

    @PostMapping("/catch")
    public ResponseEntity<String> catchMonster(@RequestParam String monsterId) {
        service.catchMonster(monsterId);
        return ResponseEntity.ok("몬스터가 도감에 추가되었습니다.");
    }
}
