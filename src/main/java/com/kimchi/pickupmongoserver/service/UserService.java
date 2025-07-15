package com.kimchi.pickupmongoserver.service;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import com.kimchi.pickupmongoserver.entity.User;
import com.kimchi.pickupmongoserver.util.JwtUtil;
import com.kimchi.pickupmongoserver.dto.LoginRequest;
import com.kimchi.pickupmongoserver.dto.SignupRequest;
import com.kimchi.pickupmongoserver.dto.TokenResponse;
import com.kimchi.pickupmongoserver.repository.UserRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.core.context.SecurityContextHolder;

@Service
@RequiredArgsConstructor
public class UserService {
    
    private final UserRepository repository;
    private final PasswordEncoder passwordEncoder;
    private final JwtUtil jwtUtil;
    
    public TokenResponse signup(SignupRequest request) {
        if (repository.existsByUserId(request.getUserId())) {
            throw new RuntimeException("User ID already exists");
        }
        
        User user = new User();
        user.setUserId(request.getUserId());
        user.setPassword(passwordEncoder.encode(request.getPassword()));
        user.setName(request.getName());
        user.setRole(User.Role.USER);
        
        repository.save(user);
        
        String accessToken = jwtUtil.generateAccessToken(user.getUserId());
        String refreshToken = jwtUtil.generateRefreshToken(user.getUserId());
        
        return new TokenResponse(accessToken, refreshToken);
    }
    
    public TokenResponse login(LoginRequest request) {
        User user = repository.findByUserId(request.getUserId())
                .orElseThrow(() -> new RuntimeException("Invalid credentials"));
        
        if (!passwordEncoder.matches(request.getPassword(), user.getPassword())) {
            throw new RuntimeException("Invalid credentials");
        }
        
        String accessToken = jwtUtil.generateAccessToken(user.getUserId());
        String refreshToken = jwtUtil.generateRefreshToken(user.getUserId());
        
        return new TokenResponse(accessToken, refreshToken);
    }
    
    public User me() {
        UserDetails user = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        return repository.findByUserId(user.getUsername())
            .orElseThrow(() -> new RuntimeException("UserNotFound"));
    }

    public Integer xp(int xp) {
        UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        User user = repository.findByName(userDetails.getUsername())
            .orElseThrow(() -> new RuntimeException("UserNotFound"));
        user.setXp(user.getXp() + xp);
        repository.save(user);
        return user.getXp();
    }
}