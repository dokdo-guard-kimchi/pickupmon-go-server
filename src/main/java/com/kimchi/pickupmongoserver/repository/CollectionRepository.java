package com.kimchi.pickupmongoserver.repository;

import com.kimchi.pickupmongoserver.entity.Collection;
import com.kimchi.pickupmongoserver.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CollectionRepository extends JpaRepository<Collection, Long> {
    boolean existsByUserAndImageUrl(User user, String imageUrl);

    List<String> findByUser(User user);
}
