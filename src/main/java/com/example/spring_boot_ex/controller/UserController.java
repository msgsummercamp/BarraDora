package com.example.spring_boot_ex.controller;

import com.example.spring_boot_ex.model.UserModel;
import com.example.spring_boot_ex.service.UserService;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/users")
@Slf4j
public class UserController {
    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }
    @GetMapping("/{id}")
    public ResponseEntity<UserModel> getUser(@PathVariable Long id) {
        log.info("GET /users/{} called", id);
        UserModel usermodel = userService.getUserById(id);
        log.info("User with id {} retrieved successfully", id);
        return ResponseEntity.ok(usermodel);
    }

    @GetMapping
    public ResponseEntity<List<UserModel>> getAllUsers() {
        log.info("GET /users called");
        List<UserModel> users = userService.getAllUsers();
        log.info("List of users retrieved succesfully");
        log.info("Returned {} users", users.size());
        return ResponseEntity.ok(users);
    }

    @PostMapping
    public ResponseEntity<UserModel> createUser(@Valid @RequestBody UserModel usermodel) {
        log.info("POST /users called with user: {}", usermodel);
        UserModel createdUser = userService.createUser(usermodel);
        log.info("User created with ID: {}", createdUser.getId());
        return ResponseEntity.ok(createdUser);
    }

}
