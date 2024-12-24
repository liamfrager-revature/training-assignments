package com.liamfrager.connect.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;

import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import com.liamfrager.connect.AuthUtil;
import com.liamfrager.connect.entity.User;
import com.liamfrager.connect.exception.InvalidLoginException;
import com.liamfrager.connect.exception.UserAlreadyExistsException;
import com.liamfrager.connect.repository.UserRepository;

@ExtendWith(MockitoExtension.class)
public class AuthServiceTest {
    
    @InjectMocks
    private AuthService authService;

    @Mock
    private UserRepository userRepository;

    @Test
    void testRegisterSuccess() throws Exception {
        User newUser = new User("testUser", "test@example.com", "securePass");
        Mockito.when(userRepository.findByUsername(newUser.getUsername())).thenReturn(Optional.empty());
        Mockito.when(userRepository.findByEmail(newUser.getEmail())).thenReturn(Optional.empty());
        Mockito.when(userRepository.save(any(User.class))).thenReturn(newUser);

        User registeredUser = authService.register(newUser);

        assertEquals(newUser.getUsername(), registeredUser.getUsername());
    }

    @Test
    void testRegisterWithExistingUser() {
        User existingUser = new User("testUser", "test@example.com", "securePass");
        Mockito.when(userRepository.findByUsername(existingUser.getUsername())).thenReturn(Optional.of(existingUser));

        assertThrows(UserAlreadyExistsException.class, () -> authService.register(existingUser));
    }

    @Test
    void testLoginSuccess() throws Exception {
        User existingUser = new User("testUser", "test@example.com", "securePass");
        existingUser.setPassword(AuthUtil.hashPassword(existingUser.getPassword()));
        Mockito.when(userRepository.findByUsername(existingUser.getUsername())).thenReturn(Optional.of(existingUser));

        User loggedInUser = authService.login(new User("testUser", null, "securePass"));

        assertEquals(existingUser.getUsername(), loggedInUser.getUsername());
    }

    @Test
    void testLoginInvalidCredentials() {
        User invalidUser = new User("testUser", null, "wrongPass");
        Mockito.when(userRepository.findByUsername(invalidUser.getUsername())).thenReturn(Optional.empty());

        assertThrows(InvalidLoginException.class, () -> authService.login(invalidUser));
    }
}