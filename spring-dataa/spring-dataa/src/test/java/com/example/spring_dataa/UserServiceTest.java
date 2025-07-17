package com.example.spring_dataa;

import com.example.spring_dataa.model.User;
import com.example.spring_dataa.repository.UserRepository;
import com.example.spring_dataa.service.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

class UserServiceTest {

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private UserService userService;

    private User sampleUser;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        sampleUser = new User(1L, "jdoe", "jdoe@example.com", "pass123", "John", "Doe");
    }

    @Test
    void testSaveUser() {
        when(userRepository.save(sampleUser)).thenReturn(sampleUser);

        User saved = userService.saveUser(sampleUser);

        assertThat(saved).isEqualTo(sampleUser);
        verify(userRepository).save(sampleUser);
    }

    @Test
    void testGetUserById() {
        when(userRepository.findById(1L)).thenReturn(Optional.of(sampleUser));

        Optional<User> found = userService.getUserById(1L);

        assertThat(found).isPresent();
        assertThat(found.get().getUsername()).isEqualTo("jdoe");
        verify(userRepository).findById(1L);
    }

    @Test
    void testGetAllUsers() {
        List<User> users = Arrays.asList(sampleUser);
        when(userRepository.findAll()).thenReturn(users);

        List<User> result = userService.getAllUsers();

        assertThat(result).hasSize(1);
        verify(userRepository).findAll();
    }

    @Test
    void testGetUserByUsername() {
        when(userRepository.findByUsername("jdoe")).thenReturn(Optional.of(sampleUser));

        Optional<User> user = userService.getUserByUsername("jdoe");

        assertThat(user).isPresent();
        assertThat(user.get().getEmail()).isEqualTo("jdoe@example.com");
    }

    @Test
    void testGetUserByEmail() {
        when(userRepository.findByEmail("jdoe@example.com")).thenReturn(Optional.of(sampleUser));

        Optional<User> user = userService.getUserByEmail("jdoe@example.com");

        assertThat(user).isPresent();
        assertThat(user.get().getUsername()).isEqualTo("jdoe");
    }

    @Test
    void testDeleteUserById() {
        userService.deleteUserById(1L);

        verify(userRepository).deleteById(1L);
    }

    @Test
    void testCountUsers() {
        when(userRepository.countUsers()).thenReturn(5L);
        long count = userService.getTotalUserCount();
        assertThat(count).isEqualTo(5L);
    }
}
