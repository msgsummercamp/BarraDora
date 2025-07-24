package com.example.spring_boot_ex.service;

import com.example.spring_boot_ex.model.UserModel;
import com.example.spring_boot_ex.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class UserServiceUnitTest {

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private UserService userService;

    @BeforeEach
    void setup() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void returnUserById_userExists() {
        Long userId = 1L;
        String expectedName = "Alice";
        String expectedEmail = "alice@gmail.com";

        UserModel user = new UserModel(userId, expectedName, expectedEmail);
        when(userRepository.findById(userId)).thenReturn(Optional.of(user));

        UserModel result = userService.getUserById(userId);

        assertThat(result.getName(), is(expectedName));
        assertThat(result.getEmail(), equalTo(expectedEmail));
        verify(userRepository, times(1)).findById(userId);
    }

    @Test
    void returnAllUsers_success() {
        List<UserModel> mockUsers = Arrays.asList(
                new UserModel(1L, "Alice", "alice@gmail.com"),
                new UserModel(2L, "Bob", "bob@gmail.com")
        );
        when(userRepository.findAll()).thenReturn(mockUsers);

        List<UserModel> result = userService.getAllUsers();

        assertThat(result, hasSize(2));
        assertThat(result.get(0).getName(), is("Alice"));
        verify(userRepository).findAll();
    }

    @Test
    void createUser_success() {
        Long userId = 5L;
        String expectedName = "Charlie";
        String expectedEmail = "charlie@gmail.com";

        UserModel newUser = new UserModel(userId, expectedName, expectedEmail);
        when(userRepository.save(newUser)).thenReturn(newUser);

        UserModel result = userService.createUser(newUser);

        assertThat(result.getName(), is(expectedName));
        assertThat(result.getEmail(), equalTo(expectedEmail));
        verify(userRepository).save(newUser);
    }

    @Test
    void getUserById_repositoryThrowsException() {
        Long userId = 99L;
        String expectedErrorMessage = "User not found with id " + userId;

        when(userRepository.findById(userId)).thenThrow(new RuntimeException(expectedErrorMessage));

        RuntimeException exception = assertThrows(RuntimeException.class, () -> userService.getUserById(userId));

        assertThat(exception.getMessage(), is(expectedErrorMessage));
        verify(userRepository, times(1)).findById(userId);
    }
}