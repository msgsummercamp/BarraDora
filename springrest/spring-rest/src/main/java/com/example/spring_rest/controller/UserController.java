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
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;

@RestController
@RequestMapping("/api/users")
@RequiredArgsConstructor
@Slf4j
public class UserController {

    private final UserService userService;

    @Operation(summary = "Create a new user")
    @ApiResponses({
            @ApiResponse(responseCode = "201", description = "User created successfully"),
            @ApiResponse(responseCode = "400", description = "Invalid user data")
    })
    @PostMapping
    public ResponseEntity<User> createUser(@Valid @RequestBody User user) {
        log.info("Creating user: {}", user);
        return ResponseEntity.status(201).body(userService.saveUser(user));
    }

    @Operation(summary = "Retrieve all users with pagination")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Users retrieved successfully")
    })
    @GetMapping
    public ResponseEntity<Page<User>> getAllUsers(Pageable pageable) {
        log.info("Fetching all users with pagination");
        return ResponseEntity.ok(userService.getAllUsers(pageable));
    }

    @Operation(summary = "Retrieve a user by ID")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "User retrieved successfully"),
            @ApiResponse(responseCode = "404", description = "User not found")
    })
    @GetMapping("/{id}")
    public ResponseEntity<User> getUserById(@PathVariable Long id) {
        log.info("Fetching user by ID: {}", id);
        return userService.getUserById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.status(404).build());
    }

    @Operation(summary = "Update an existing user")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "User updated successfully"),
            @ApiResponse(responseCode = "404", description = "User not found"),
            @ApiResponse(responseCode = "400", description = "Invalid user data")
    })
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

    @Operation(summary = "Partially update a user's information")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "User updated successfully"),
            @ApiResponse(responseCode = "404", description = "User not found")
    })
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

    @Operation(summary = "Delete a user by ID")
    @ApiResponses({
            @ApiResponse(responseCode = "204", description = "User deleted successfully"),
            @ApiResponse(responseCode = "404", description = "User not found")
    })
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