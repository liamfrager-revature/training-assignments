package com.liamfrager.connect.unit_tests;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Optional;
import java.util.Set;

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

        User result = userService.register(user);

        assertEquals(user, result);
    }

    @Test
    void testLogin() throws InvalidLoginException {
        User user = TestData.generateUser();
        when(userRepository.findByUsernameAndPassword("username", "password")).thenReturn(Optional.of(user));

        User result = userService.login(user);

        assertEquals(user, result);
    }

    
    @Test
    void testGetAllFriendsByUserID() throws InvalidUserException {
        when(userRepository.findById(1L)).thenReturn(Optional.of(user1));

        Set<User> friends = userService.getAllFriendsByUserID(1L);

        assertNotNull(friends);
        assertEquals(1, friends.size());
        assertTrue(friends.contains(user2));

        verify(userRepository, times(1)).findById(1L);
    }

    @Test
    void testGetAllFriendsByUserID_UserNotFound() {
        when(userRepository.findById(99L)).thenReturn(Optional.empty());

        assertThrows(InvalidUserException.class, () -> userService.getAllFriendsByUserID(99L));

        verify(userRepository, times(1)).findById(99L);
    }

    @Test
    void testGetAllFollowersByUserID() throws InvalidUserException {
        when(userRepository.findById(1L)).thenReturn(Optional.of(user1));

        Set<User> followers = userService.getAllFollowersByUserID(1L);

        assertNotNull(followers);
        assertEquals(1, followers.size());
        assertTrue(followers.contains(user3));

        verify(userRepository, times(1)).findById(1L);
    }

    @Test
    void testGetAllFollowersByUserID_UserNotFound() {
        when(userRepository.findById(99L)).thenReturn(Optional.empty());

        assertThrows(InvalidUserException.class, () -> userService.getAllFollowersByUserID(99L));

        verify(userRepository, times(1)).findById(99L);
    }

    @Test
    void testFollowUser() throws InvalidUserException {
        when(userRepository.findById(1L)).thenReturn(Optional.of(user1));
        when(userRepository.findById(2L)).thenReturn(Optional.of(user2));

        userService.followUser(1L, 2L);

        assertTrue(user1.getFollowing().contains(user2));
        assertTrue(user2.getFollowers().contains(user1));

        verify(userRepository, times(1)).findById(1L);
        verify(userRepository, times(1)).findById(2L);
        verify(userRepository, times(1)).save(user1);
    }

    @Test
    void testFollowUser_FollowerNotFound() {
        when(userRepository.findById(1L)).thenReturn(Optional.empty());

        assertThrows(InvalidUserException.class, () -> userService.followUser(1L, 2L));

        verify(userRepository, times(1)).findById(1L);
        verify(userRepository, times(0)).findById(2L);
    }

    @Test
    void testFollowUser_FolloweeNotFound() {
        when(userRepository.findById(1L)).thenReturn(Optional.of(user1));
        when(userRepository.findById(2L)).thenReturn(Optional.empty());

        assertThrows(InvalidUserException.class, () -> userService.followUser(1L, 2L));

        verify(userRepository, times(1)).findById(1L);
        verify(userRepository, times(1)).findById(2L);
    }
}