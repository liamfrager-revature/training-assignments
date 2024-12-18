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

import com.liamfrager.connect.entity.User;
import com.liamfrager.connect.exception.InvalidFollowException;
import com.liamfrager.connect.exception.InvalidUserException;
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
    }
    
    @Test
    void testGetAllFollowingByUserID() throws InvalidUserException {
        when(userRepository.findById(1L)).thenReturn(Optional.of(user1));

        Set<User> following = userService.getAllFollowingByUserID(1L);

        assertNotNull(following);
        assertEquals(1, following.size());
        assertTrue(following.contains(user2));

        verify(userRepository, times(1)).findById(1L);
    }

    @Test
    void testGetAllFollowingByUserID_UserNotFound() {
        when(userRepository.findById(99L)).thenReturn(Optional.empty());

        assertThrows(InvalidUserException.class, () -> userService.getAllFollowingByUserID(99L));

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
    void testFollowUser() throws InvalidUserException, InvalidFollowException {
        when(userRepository.findById(1L)).thenReturn(Optional.of(user1));
        when(userRepository.findById(2L)).thenReturn(Optional.of(user2));

        userService.followUser(1L, 2L);

        assertTrue(user1.getFollowing().stream().anyMatch(follow -> follow.getFollowee().equals(user2)));
        assertTrue(user2.getFollowers().stream().anyMatch(follow -> follow.getFollower().equals(user1)));

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
