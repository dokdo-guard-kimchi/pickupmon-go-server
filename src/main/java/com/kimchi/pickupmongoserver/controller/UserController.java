package com.kimchi.pickupmongoserver.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import com.kimchi.pickupmongoserver.entity.User;
import com.kimchi.pickupmongoserver.service.UserService;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/user")
@RequiredArgsConstructor
public class UserController {
    private final UserService service;

    @GetMapping("/me")
    public ResponseEntity<User> me() {
        return ResponseEntity.ok(service.me());
    }

    @PostMapping("/xp")
    public ResponseEntity<Integer> xp(@RequestParam int xp) {
        return ResponseEntity.ok(service.xp(xp));
    }
}
