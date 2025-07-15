package com.kimchi.pickupmongoserver.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import com.kimchi.pickupmongoserver.entity.User;
import com.kimchi.pickupmongoserver.service.UserService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestMapping;

@RestController
@RequestMapping("/user")
@RequiredArgsConstructor
public class UserController {
    private final UserService service;

    @GetMapping("/me")
    public ResponseEntity<User> me() {
        return ResponseEntity.ok(service.me());
    }
}
