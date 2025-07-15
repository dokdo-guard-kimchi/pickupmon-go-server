package com.kimchi.pickupmongoserver.controller;

import com.kimchi.pickupmongoserver.entity.Monster;
import com.kimchi.pickupmongoserver.service.MonsterService;
import com.kimchi.pickupmongoserver.service.PhotoService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/monster")
@RequiredArgsConstructor
public class MonsterController {
    private final MonsterService service;
    private final PhotoService photoService;

    @GetMapping("/find")
    public ResponseEntity<Monster> findMonster(@RequestParam Monster.MonsterType type) {
        return ResponseEntity.ok(service.findMonster(type));
    }

    @PostMapping("/catch")
    public ResponseEntity<String> catchMonster(@RequestParam("file") MultipartFile file) {
        String url = photoService.photoToUrl(file);
        service.catchMonster(url);
        return ResponseEntity.ok("몬스터가 도감에 추가되었습니다.");
    }
}
