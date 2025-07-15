package com.kimchi.pickupmongoserver.repository;

import com.google.firebase.remoteconfig.User;
import com.kimchi.pickupmongoserver.entity.Collection;
import com.kimchi.pickupmongoserver.entity.Monster;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CollectionRepository extends JpaRepository<Collection, Long> {

}
