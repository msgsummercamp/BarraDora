package com.example.springsecurity.controller;

import com.example.springsecurity.dto.SignInRequest;
import com.example.springsecurity.dto.SignInResponse;
import com.example.springsecurity.model.User;
import com.example.springsecurity.repository.RoleRepository;
import com.example.springsecurity.service.AuthService;
import com.example.springsecurity.service.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.hibernate.validator.internal.util.stereotypes.Lazy;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthController {

    @Lazy
    private final AuthService authService;
    private final UserService userService;
    private final RoleRepository roleRepository;

    @PostMapping("/login")
    public ResponseEntity<SignInResponse> login(@Valid @RequestBody SignInRequest request) {
        return ResponseEntity.ok(authService.authenticate(request));
    }

    @PostMapping("/register")
    public ResponseEntity<User> register(@Valid @RequestBody User user) {
        user.setPassword(authService.encodePassword(user.getPassword()));

        // Ensure roles are not duplicated
        user.setRoles(user.getRoles().stream()
                .map(role -> roleRepository.findByName(role.getName())
                        .orElseGet(() -> roleRepository.save(role)))
                .collect(Collectors.toSet()));

        return ResponseEntity.status(201).body(userService.saveUser(user));
    }
}