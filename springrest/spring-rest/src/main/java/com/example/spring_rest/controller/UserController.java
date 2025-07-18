package com.example.spring_rest.controller;

import com.example.spring_rest.model.User;
import com.example.spring_rest.service.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/users")
@RequiredArgsConstructor
@Slf4j
public class UserController {

    private final UserService userService;

    @PostMapping
    public ResponseEntity<User> createUser(@Valid @RequestBody User user) {
        log.info("Creating user: {}", user);
        return ResponseEntity.status(201).body(userService.saveUser(user));
    }

    @GetMapping
    public ResponseEntity<Page<User>> getAllUsers(Pageable pageable) {
        log.info("Fetching all users with pagination");
        return ResponseEntity.ok(userService.getAllUsers(pageable));
    }

    @GetMapping("/{id}")
    public ResponseEntity<User> getUserById(@PathVariable Long id) {
        log.info("Fetching user by ID: {}", id);
        return userService.getUserById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.status(404).build());
    }

    @PutMapping("/{id}")
    public ResponseEntity<User> updateUser(@PathVariable Long id, @Valid @RequestBody User updatedUser) {
        log.info("Updating user with ID: {}", id);
        return userService.getUserById(id)
                .map(existingUser -> {
                    updatedUser.setId(id);
                    return ResponseEntity.ok(userService.saveUser(updatedUser));
                })
                .orElse(ResponseEntity.status(404).build());
    }

    @PatchMapping("/{id}")
    public ResponseEntity<User> partiallyUpdateUser(@PathVariable Long id, @RequestBody User partialUpdate) {
        log.info("Partially updating user with ID: {}", id);
        return userService.getUserById(id)
                .map(existingUser -> {
                    if (partialUpdate.getUsername() != null) {
                        existingUser.setUsername(partialUpdate.getUsername());
                    }
                    if (partialUpdate.getEmail() != null) {
                        existingUser.setEmail(partialUpdate.getEmail());
                    }
                    if (partialUpdate.getPassword() != null) {
                        existingUser.setPassword(partialUpdate.getPassword());
                    }
                    if (partialUpdate.getFirstName() != null) {
                        existingUser.setFirstName(partialUpdate.getFirstName());
                    }
                    if (partialUpdate.getLastName() != null) {
                        existingUser.setLastName(partialUpdate.getLastName());
                    }
                    return ResponseEntity.ok(userService.saveUser(existingUser));
                })
                .orElse(ResponseEntity.status(404).build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteUser(@PathVariable Long id) {
        log.info("Deleting user by ID: {}", id);
        if (userService.getUserById(id).isPresent()) {
            userService.deleteUserById(id);
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.status(404).build();
    }
}