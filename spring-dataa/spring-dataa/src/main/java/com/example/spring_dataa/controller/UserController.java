package com.example.spring_dataa.controller;

import com.example.spring_dataa.model.User;
import com.example.spring_dataa.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/users")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @PostMapping
    public ResponseEntity<User> createUser(@RequestBody User user) {
        return ResponseEntity.ok(userService.saveUser(user));
    }

    @GetMapping
    public ResponseEntity<List<User>> getAllUsers() {
        return ResponseEntity.ok(userService.getAllUsers());
    }

    @GetMapping("/{id}")
    public ResponseEntity<User> getUserById(@PathVariable Long id) {
        return userService.getUserById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteUser(@PathVariable Long id) {
        userService.deleteUserById(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/search")
    public ResponseEntity<User> findByUsernameOrEmail(@RequestParam(required = false) String username,
                                                      @RequestParam(required = false) String email) {
        if (username != null) {
            return userService.getUserByUsername(username)
                    .map(ResponseEntity::ok)
                    .orElse(ResponseEntity.notFound().build());
        } else if (email != null) {
            return userService.getUserByEmail(email)
                    .map(ResponseEntity::ok)
                    .orElse(ResponseEntity.notFound().build());
        } else {
            return ResponseEntity.badRequest().build();
        }
    }
}
