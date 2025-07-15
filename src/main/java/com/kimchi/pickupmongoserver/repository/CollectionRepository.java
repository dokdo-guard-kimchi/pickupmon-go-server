package com.kimchi.pickupmongoserver.repository;

import com.kimchi.pickupmongoserver.entity.Collection;
import com.kimchi.pickupmongoserver.entity.Monster;
import com.kimchi.pickupmongoserver.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CollectionRepository extends JpaRepository<Collection, Long> {
    boolean existsByUserAndMonster(User user, Monster monster);
}
