package com.kimchi.pickupmongoserver.controller;

import com.kimchi.pickupmongoserver.service.CollectionService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/collection")
@RequiredArgsConstructor
public class CollectionController {
    private final CollectionService service;

    @GetMapping
    public ResponseEntity<List<String>> getCollections() {
        return ResponseEntity.ok(service.getCollections());
    }
}
