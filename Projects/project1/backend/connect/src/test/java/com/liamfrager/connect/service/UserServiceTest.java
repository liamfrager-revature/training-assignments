package com.liamfrager.connect.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.liamfrager.connect.TestData;
import com.liamfrager.connect.entity.Follow;
import com.liamfrager.connect.entity.User;
import com.liamfrager.connect.exception.InvalidFollowException;
import com.liamfrager.connect.exception.InvalidUserException;
import com.liamfrager.connect.repository.FollowRepository;
import com.liamfrager.connect.repository.UserRepository;

@ExtendWith(MockitoExtension.class)
public class UserServiceTest {
    @InjectMocks
    private UserService userService;

    @Mock
    private UserRepository userRepository;

    @Mock
    private FollowRepository followRepository;
    
    @Test
    void testGetAllFollowingByUserID() throws InvalidUserException {
        User user = TestData.generateUser();
        when(followRepository.findFollowing(user.getId())).thenReturn(Optional.of(List.of()));

        List<User> following = userService.getAllFollowingByUserID(user.getId());

        assertNotNull(following);
        assertEquals(0, following.size());
    }

    @Test
    void testGetAllFollowingByUserIDUserNotFound() {
        User user = TestData.generateUser();
        when(followRepository.findFollowing(user.getId())).thenReturn(Optional.empty());

        assertThrows(InvalidUserException.class, () -> userService.getAllFollowingByUserID(user.getId()));
    }

    @Test
    void testGetAllFollowersByUserID() throws InvalidUserException {
        User user = TestData.generateUser();
        when(followRepository.findFollowers(user.getId())).thenReturn(Optional.of(List.of()));

        List<User> followers = userService.getAllFollowersByUserID(user.getId());

        assertNotNull(followers);
        assertEquals(0, followers.size());
    }

    @Test
    void testGetAllFollowersByUserIDUserNotFound() {
        User user = TestData.generateUser();
        when(followRepository.findFollowers(user.getId())).thenReturn(Optional.empty());

        assertThrows(InvalidUserException.class, () -> userService.getAllFollowersByUserID(user.getId()));
    }

    @Test
    void testFollowUser() throws InvalidUserException, InvalidFollowException {
        User follower = TestData.generateNewUser();
        follower.setId(1L);
        User followee = TestData.generateUser();
        followee.setId(2L);
        when(userRepository.findById(follower.getId())).thenReturn(Optional.of(follower));
        when(userRepository.findById(followee.getId())).thenReturn(Optional.of(followee));
        when(followRepository.existsByFollowerAndFollowee(follower.getId(), followee.getId())).thenReturn(false);

        userService.followUser(follower.getId(), followee.getId());


        verify(followRepository, times(1)).save(any(Follow.class));
    }

    @Test
    void testFollowUserFollowerNotFound() {
        when(userRepository.findById(1L)).thenReturn(Optional.empty());

        assertThrows(InvalidUserException.class, () -> userService.followUser(1L, 2L));

        verify(userRepository, times(1)).findById(1L);
        verify(userRepository, times(0)).findById(2L);
    }

    @Test
    void testFollowUserFolloweeNotFound() {
        User user = TestData.generateUser();
        when(userRepository.findById(1L)).thenReturn(Optional.of(user));
        when(userRepository.findById(2L)).thenReturn(Optional.empty());

        assertThrows(InvalidUserException.class, () -> userService.followUser(1L, 2L));

        verify(userRepository, times(1)).findById(1L);
        verify(userRepository, times(1)).findById(2L);
    }
}
