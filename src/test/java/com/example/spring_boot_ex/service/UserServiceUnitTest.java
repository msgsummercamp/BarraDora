package com.example.spring_boot_ex.service;

import com.example.spring_boot_ex.model.UserModel;
import com.example.spring_boot_ex.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class UserServiceUnitTest {

    private UserRepository userRepository;
    private UserService userService;

    @BeforeEach
    void setup() {
        userRepository = mock(UserRepository.class);
        userService = new UserService(userRepository);
    }

    @Test
    void shouldReturnUserById_whenUserExists() {
        UserModel user = new UserModel(1L, "Alice", "alice@gmail.com");
        when(userRepository.findById(1L)).thenReturn(Optional.of(user));

        UserModel result = userService.getUserById(1L);

        assertThat(result.getName(), is("Alice"));
        assertThat(result.getEmail(), containsString("@gmail.com"));
        verify(userRepository, times(1)).findById(1L);
    }

    @Test
    void shouldReturnAllUsers() {
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
    void shouldCreateUserSuccessfully() {
        UserModel newUser = new UserModel(5L, "Charlie", "charlie@gmail.com");
        when(userRepository.save(newUser)).thenReturn(newUser);

        UserModel result = userService.createUser(newUser);

        assertThat(result.getName(), is("Charlie"));
        assertThat(result.getEmail(), equalTo("charlie@gmail.com"));
        verify(userRepository).save(newUser);
    }
}
