package com.liamfrager.connect.unit_tests;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;

import com.liamfrager.connect.TestData;
import com.liamfrager.connect.entity.User;
import com.liamfrager.connect.exception.InvalidLoginException;
import com.liamfrager.connect.exception.InvalidPasswordException;
import com.liamfrager.connect.exception.InvalidUsernameException;
import com.liamfrager.connect.exception.UserAlreadyExistsException;
import com.liamfrager.connect.repository.UserRepository;
import com.liamfrager.connect.service.AuthService;

@ExtendWith(MockitoExtension.class)
public class AuthServiceTest {
    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private AuthService authService;

    private User user1;
    private User user2;
    private User user3;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);

        user1 = new User("user1", "user1@example.com", "password1");
        user1.setId(1L);

        user2 = new User("user2", "user2@example.com", "password2");
        user2.setId(2L);

        user3 = new User("user3", "user3@example.com", "password3");
        user3.setId(3L);

        user1.getFollowing().add(user2);
        user1.getFollowers().add(user3);

        user2.getFollowers().add(user1);
        user3.getFollowing().add(user1);
    }

    @Test
    void testRegister() throws InvalidUsernameException, InvalidPasswordException, UserAlreadyExistsException {
        User user = TestData.generateUser();
        when(userRepository.findByUsername("username")).thenReturn(Optional.empty());
        when(userRepository.save(user)).thenReturn(user);

        User result = authService.register(user);

        assertEquals(user, result);
    }

    @Test
    void testLogin() throws InvalidLoginException {
        User user = TestData.generateUser();
        when(userRepository.findByUsernameAndPassword("username", "password")).thenReturn(Optional.of(user));

        User result = authService.login(user);

        assertEquals(user, result);
    }
}
