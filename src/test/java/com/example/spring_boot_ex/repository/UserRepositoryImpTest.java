package com.example.spring_boot_ex.repository;

import com.example.spring_boot_ex.model.UserModel;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;

class UserRepositoryImpTest {

    private UserRepositoryImp userRepository;

    @BeforeEach
    void setup() {
        userRepository = new UserRepositoryImp();
    }

    @Test
    void findById_userExists() {
        String expectedName = "Alice";

        Optional<UserModel> result = userRepository.findById(1L);

        assertTrue(result.isPresent());
        assertEquals(expectedName, result.get().getName());
    }

    @Test
    void returnAllUsers() {
        List<UserModel> users = userRepository.findAll();

        assertThat(users, hasSize(3));
        assertThat(users.stream().map(UserModel::getName).toList(), containsInAnyOrder("Alice", "Bob", "Dave"));
    }

    @Test
    void saveNewUser() {
        String expectedName = "Eve";
        String expectedEmail = "eve@example.com";

        UserModel newUser = new UserModel(4L, expectedName, expectedEmail);
        userRepository.save(newUser);
        Optional<UserModel> result = userRepository.findById(4L);

        assertTrue(result.isPresent());
        assertEquals(expectedName, result.get().getName());
        assertEquals(expectedEmail, result.get().getEmail());
    }
}

