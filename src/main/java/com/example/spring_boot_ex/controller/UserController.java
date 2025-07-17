package com.example.spring_boot_ex.controller;

import com.example.spring_boot_ex.model.UserModel;
import com.example.spring_boot_ex.service.UserService;
import jakarta.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/users")
public class UserController {
    private final UserService userService;
    private static final Logger logger = LoggerFactory.getLogger(UserController.class);

    public UserController(UserService userService) {
        this.userService = userService;
    }
    @GetMapping("/{id}")
    public ResponseEntity<UserModel> getUser(@PathVariable Long id) {
        logger.info("GET /users/{} called", id);
        UserModel usermodel = userService.getUserById(id);
        logger.info("User with id {} retrieved successfully", id);
        return ResponseEntity.ok(usermodel);
    }

    @GetMapping
    public ResponseEntity<List<UserModel>> getAllUsers() {
        logger.info("GET /users called");
        List<UserModel> users = userService.getAllUsers();
        logger.info("List of users retrieved succesfully");
        logger.info("Returned {} users", users.size());
        return ResponseEntity.ok(users);
    }

    @PostMapping
    public ResponseEntity<UserModel> createUser(@Valid @RequestBody UserModel usermodel) {
        logger.info("POST /users called with user: {}", usermodel);
        UserModel createdUser = userService.createUser(usermodel);
        logger.info("User created with ID: {}", createdUser.getId());
        return ResponseEntity.ok(createdUser);
    }

}
