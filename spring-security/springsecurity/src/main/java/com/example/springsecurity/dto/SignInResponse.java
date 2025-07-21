package com.example.springsecurity.dto;

import lombok.Data;

import java.util.Set;

@Data
public class SignInResponse {
    private String token;
    private Set<String> roles;
}