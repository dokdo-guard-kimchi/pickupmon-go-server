package com.kimchi.pickupmongoserver.service;

import com.kimchi.pickupmongoserver.entity.User;
import com.kimchi.pickupmongoserver.repository.CollectionRepository;
import com.kimchi.pickupmongoserver.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CollectionService {
    private final CollectionRepository repository;
    private final UserRepository userRepository;

    public List<String> getCollections() {
        UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        User user = userRepository.findByName(userDetails.getUsername())
            .orElseThrow(() -> new RuntimeException("User not found"));
        return repository.findByUser(user);
    }
}
