package com.kimchi.pickupmongoserver.service;

import com.kimchi.pickupmongoserver.entity.Collection;
import com.kimchi.pickupmongoserver.entity.Monster;
import com.kimchi.pickupmongoserver.entity.User;
import com.kimchi.pickupmongoserver.repository.CollectionRepository;
import com.kimchi.pickupmongoserver.repository.MonsterRepository;
import com.kimchi.pickupmongoserver.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class MonsterService {
    private final MonsterRepository repository;
    private final UserRepository userRepository;
    private final CollectionRepository collectionRepository;

    public Monster findMonster(Monster.MonsterType type) {
        Monster monster = new Monster();
        monster.setType(type);
        repository.save(monster);
        return monster;
    }

    public void catchMonster(String monsterId) {
        UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        User user = userRepository.findByName(userDetails.getUsername())
            .orElseThrow(() -> new RuntimeException("User not found"));
        Monster monster = repository.findById(monsterId)
            .orElseThrow(() -> new RuntimeException("Monster not found"));
        Collection collection = Collection.builder()
                .user(user)
                .monster(monster)
                .build();
        if (collectionRepository.existsByUserAndMonster(user, monster)) {
            throw new RuntimeException("Already caught monster");
        }
        collectionRepository.save(collection);
    }
}
