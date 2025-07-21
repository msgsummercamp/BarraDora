package com.example.springsecurity.service;

import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;

import java.util.Base64;

public class SecretKeyGenerator {
    public static void main(String[] args) {
        String secureKey = Base64.getEncoder().encodeToString(Keys.secretKeyFor(SignatureAlgorithm.HS512).getEncoded());
        System.out.println("Generated Secure Key: " + secureKey);
    }
}