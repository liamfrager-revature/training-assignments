package com.liamfrager.connect.unit_tests;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;

import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.liamfrager.connect.TestData;
import com.liamfrager.connect.entity.User;
import com.liamfrager.connect.exception.InvalidLoginException;
import com.liamfrager.connect.exception.InvalidPasswordException;
import com.liamfrager.connect.exception.InvalidUserException;
import com.liamfrager.connect.exception.InvalidUsernameException;
import com.liamfrager.connect.exception.UserAlreadyExistsException;
import com.liamfrager.connect.repository.UserRepository;
import com.liamfrager.connect.service.UserService;

@ExtendWith(MockitoExtension.class)
public class UserServiceTest {
    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private UserService userService;

    @Test
    void register_ShouldSaveUser_WhenValid() throws InvalidUsernameException, InvalidPasswordException, UserAlreadyExistsException {
        User user = TestData.generateUser();
        when(userRepository.findByUsername("username")).thenReturn(Optional.empty());
        when(userRepository.save(user)).thenReturn(user);

        User result = userService.register(user);

        assertEquals(user, result);
    }

    @Test
    void login_ShouldReturnUser_WhenCredentialsMatch() throws InvalidLoginException {
        User user = TestData.generateUser();
        when(userRepository.findByUsernameAndPassword("username", "password")).thenReturn(Optional.of(user));

        User result = userService.login(user);

        assertEquals(user, result);
    }

    @Test
    void getAllFriendsByUserID_ShouldThrowException_WhenUserNotFound() {
        when(userRepository.findById(1L)).thenReturn(Optional.empty());

        assertThrows(InvalidUserException.class, () -> userService.getAllFriendsByUserID(1L));
    }
}
