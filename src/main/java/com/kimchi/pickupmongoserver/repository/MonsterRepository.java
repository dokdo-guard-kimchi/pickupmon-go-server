package com.kimchi.pickupmongoserver.repository;

import com.kimchi.pickupmongoserver.entity.Monster;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface MonsterRepository extends JpaRepository<Monster, String> {
    Optional<Monster> findById(String id);
}
