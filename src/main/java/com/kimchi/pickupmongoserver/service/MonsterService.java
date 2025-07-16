package com.kimchi.pickupmongoserver.service;

import com.kimchi.pickupmongoserver.entity.Collection;
import com.kimchi.pickupmongoserver.entity.Monster;
import com.kimchi.pickupmongoserver.entity.User;
import com.kimchi.pickupmongoserver.repository.CollectionRepository;
import com.kimchi.pickupmongoserver.repository.MonsterRepository;
import com.kimchi.pickupmongoserver.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class MonsterService {
    private final MonsterRepository repository;
    private final UserRepository userRepository;
    private final CollectionRepository collectionRepository;
    private final CharacterGenerationService characterGenerationService;

    public Monster findMonster(Monster.MonsterType type) {
        Monster monster = new Monster();
        monster.setType(type);
        repository.save(monster);
        return monster;
    }

    public void catchMonster(String url) {
        UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        User user = userRepository.findByName(userDetails.getUsername())
            .orElseThrow(() -> new RuntimeException("User not found"));
        Collection collection = Collection.builder()
                .user(user)
                .imageUrl(url)
                .build();
        if (collectionRepository.existsByUserAndImageUrl(user, url)) {
            throw new RuntimeException("Already caught monster");
        }
        collectionRepository.save(collection);
    }

    public Monster generateMonsterFromTrash(String imageUrl) {
        try {
            CharacterGenerationService.CharacterInfo characterInfo = 
                characterGenerationService.generateCharacterFromTrashImage(imageUrl);
            
            Monster.MonsterType type = determineMonsterType(characterInfo.getWasteType());
            
            Monster monster = new Monster();
            monster.setType(type);
            monster.setName(characterInfo.getCharacterName());
            monster.setSkills(characterInfo.getSkills());
            monster.setImageUrl(imageUrl);
            
            return repository.save(monster);
        } catch (Exception e) {
            log.error("몬스터 생성 실패: {}", e.getMessage());
            throw new RuntimeException("Monster generation failed", e);
        }
    }

    private Monster.MonsterType determineMonsterType(String wasteType) {
        String lowerWasteType = wasteType.toLowerCase();
        if (lowerWasteType.contains("캔") || lowerWasteType.contains("can") || lowerWasteType.contains("알루미늄")) {
            return Monster.MonsterType.CAN;
        } else if (lowerWasteType.contains("플라스틱") || lowerWasteType.contains("plastic") || lowerWasteType.contains("병")) {
            return Monster.MonsterType.PLASTIC;
        } else {
            return Monster.MonsterType.ETC;
        }
    }
}
