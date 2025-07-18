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
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

class UserServiceTest {

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private UserService userService;

    private User sampleUser;
    private long id;
    private String username;
    private String email;
    private String password;
    private String firstName;
    private String lastName;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);

        id = 1L;
        username = "jdoe";
        email = "jdoe@example.com";
        password = "pass123";
        firstName = "John";
        lastName = "Doe";

        sampleUser = new User(id, username, email, password, firstName, lastName);
    }

    @Test
    void saveNewUser_or_updateExistingUser_success() {
        when(userRepository.save(sampleUser)).thenReturn(sampleUser);

        User saved = userService.saveUser(sampleUser);

        assertThat(saved).isEqualTo(sampleUser);
        verify(userRepository).save(sampleUser);
    }

    @Test
    void getUserById_userExists() {
        when(userRepository.findById(id)).thenReturn(Optional.of(sampleUser));

        Optional<User> found = userService.getUserById(id);

        assertThat(found).isPresent();
        assertThat(found.get().getUsername()).isEqualTo(username);
        verify(userRepository).findById(id);
    }

    @Test
    void getAllUsers_success() {
        List<User> users = Arrays.asList(sampleUser);
        when(userRepository.findAll()).thenReturn(users);

        List<User> result = userService.getAllUsers();

        assertThat(result).hasSize(1);
        verify(userRepository).findAll();
    }

    @Test
    void getUserByUsername_userExists() {
        when(userRepository.findByUsername(username)).thenReturn(Optional.of(sampleUser));

        Optional<User> user = userService.getUserByUsername(username);

        assertThat(user).isPresent();
        assertThat(user.get().getEmail()).isEqualTo(email);
    }

    @Test
    void getUserByEmail_userExists() {
        when(userRepository.findByEmail(email)).thenReturn(Optional.of(sampleUser));

        Optional<User> user = userService.getUserByEmail(email);

        assertThat(user).isPresent();
        assertThat(user.get().getUsername()).isEqualTo(username);
    }

    @Test
    void deleteUserById_userExists() {
        userService.deleteUserById(id);

        verify(userRepository).deleteById(id);
    }

    @Test
    void testCountUsers_success() {
        when(userRepository.countUsers()).thenReturn(5L);
        long count = userService.getTotalUserCount();
        assertThat(count).isEqualTo(5L);
    }

    @Test
    void getUserById_userRepositoryThrowsException() {
        when(userRepository.findById(id)).thenThrow(new RuntimeException("Database error"));

        RuntimeException exception = assertThrows(RuntimeException.class, () -> {
            userService.getUserById(id);
        });

        assertThat(exception.getMessage()).isEqualTo("Database error");
        verify(userRepository).findById(id);
    }
}