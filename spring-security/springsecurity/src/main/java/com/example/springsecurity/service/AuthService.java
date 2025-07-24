package com.example.springsecurity.service;

import com.example.springsecurity.dto.SignInRequest;
import com.example.springsecurity.dto.SignInResponse;

public interface AuthService {
    SignInResponse authenticate(SignInRequest request);
    String encodePassword(String password);
    String generateToken(String username, String roles);
    boolean validateToken(String token);
    String extractUsername(String token);
}