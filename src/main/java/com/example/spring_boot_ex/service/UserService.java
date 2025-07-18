package com.example.spring_boot_ex.service;

import com.example.spring_boot_ex.model.UserModel;
import com.example.spring_boot_ex.repository.UserRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Slf4j
public class UserService {
    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public UserModel getUserById(Long id) {
        log.info("Getting user with id {}", id);
        return userRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("User not found with id " + id));
    }
    public List<UserModel> getAllUsers() {
        log.info("Getting all users");
        return userRepository.findAll();
    }

    public UserModel createUser(UserModel user) {
        log.info("Creating a new user");
        return userRepository.save(user);
    }
}
