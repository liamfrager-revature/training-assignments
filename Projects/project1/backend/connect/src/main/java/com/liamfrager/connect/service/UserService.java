package com.liamfrager.connect.service;

import java.util.Set;

import org.springframework.stereotype.Service;

import com.liamfrager.connect.entity.User;
import com.liamfrager.connect.exception.*;
import com.liamfrager.connect.repository.UserRepository;

/**
 * A service for handling the <code>User</code> business logic.
 */
@Service
public class UserService {
    public UserRepository userRepository;

    /**
     * Constructor for the user service.
     */
    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    /**
     * Get data for the given user.
     * @param userID The ID of the user whose data to return.
     * @return The friends of the user.
     * @throws InvalidUserException User with id <code>userID</code> does not exist.
     */
    public User getUser(long userID) throws InvalidUserException {
        User user = userRepository.findById(userID).orElseThrow(() -> new InvalidUserException(userID));
        user.setPassword(null);
        return user;
    }

    /**
     * Get the friends of a given user.
     * @param userID The ID of the user whose friends to return.
     * @return The friends of the user.
     * @throws InvalidUserException User with id <code>userID</code> does not exist.
     */
    public Set<User> getAllFriendsByUserID(long userID) throws InvalidUserException {
        return userRepository.findById(userID).orElseThrow(() -> new InvalidUserException(userID)).getFollowing();
    }

    /**
     * Get the followers of a given user.
     * @param userID The ID of the user whose followers to return.
     * @return The followers of the user.
     * @throws InvalidUserException User with id <code>userID</code> does not exist.
     */
    public Set<User> getAllFollowersByUserID(long userID) throws InvalidUserException {
        return userRepository.findById(userID).orElseThrow(() -> new InvalidUserException(userID)).getFollowers();
    }

    /**
     * follow a given user.
     * @param userID The ID of the user whose followers to return.
     * @return The followers of the user.
     * @throws InvalidUserException User with id <code>userID</code> does not exist.
     */
    public void followUser(long followerUserID, long followeeUserID) throws InvalidUserException {
        User follower = userRepository.findById(followerUserID).orElseThrow(() -> new InvalidUserException(followerUserID));
        User followee = userRepository.findById(followeeUserID).orElseThrow(() -> new InvalidUserException(followeeUserID));

        // Add to 'following' and 'followers' relationships
        follower.getFollowing().add(followee);
        followee.getFollowers().add(follower);

        // Persist changes (only need to save one side since it's bidirectional)
        userRepository.save(follower);
    }
}
