package com.example.spring_boot_ex.service;

import com.example.spring_boot_ex.model.UserModel;
import com.example.spring_boot_ex.repository.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService {
    private final UserRepository userRepository;
    private static final Logger logger = LoggerFactory.getLogger(UserService.class);

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public UserModel getUserById(Long id) {
        logger.info("Getting user with id {}", id);
        return userRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("User not found with id " + id));
    }
    public List<UserModel> getAllUsers() {
        logger.info("Getting all users");
        return userRepository.findAll();
    }

    public UserModel createUser(UserModel user) {
        logger.info("Creating a new user");
        return userRepository.save(user);
    }
}
