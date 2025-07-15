package com.kimchi.pickupmongoserver.service;

import lombok.RequiredArgsConstructor;
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
    
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtUtil jwtUtil;
    
    public TokenResponse signup(SignupRequest request) {
        if (userRepository.existsByUserId(request.getUserId())) {
            throw new RuntimeException("User ID already exists");
        }
        
        User user = new User();
        user.setUserId(request.getUserId());
        user.setPassword(passwordEncoder.encode(request.getPassword()));
        user.setName(request.getName());
        user.setRole(User.Role.USER);
        
        userRepository.save(user);
        
        String accessToken = jwtUtil.generateAccessToken(user.getUserId());
        String refreshToken = jwtUtil.generateRefreshToken(user.getUserId());
        
        return new TokenResponse(accessToken, refreshToken);
    }
    
    public TokenResponse login(LoginRequest request) {
        User user = userRepository.findByUserId(request.getUserId())
                .orElseThrow(() -> new RuntimeException("Invalid credentials"));
        
        if (!passwordEncoder.matches(request.getPassword(), user.getPassword())) {
            throw new RuntimeException("Invalid credentials");
        }
        
        String accessToken = jwtUtil.generateAccessToken(user.getUserId());
        String refreshToken = jwtUtil.generateRefreshToken(user.getUserId());
        
        return new TokenResponse(accessToken, refreshToken);
    }
    
    public User me() {
        return userRepository.findByUserId(SecurityContextHolder.getContext().getAuthentication().getPrincipal().toString())
            .orElseThrow(() -> new RuntimeException("User not found"));
    }
}