package com.kimchi.pickupmongoserver.dto;

import lombok.Data;

@Data
public class SignupRequest {
    private String userId;
    private String password;
    private String name;
}