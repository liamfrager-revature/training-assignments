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
     * Register a user with a given user if the user does not exist.
     * @param user The user of the user to register.
     * @return The user of the registered user.
     * @throws InvalidUsernameException <code>user.username</code> is blank.
     * @throws InvalidPasswordException <code>user.password</code> is less than 4 characters long.
     * @throws UserAlreadyExistsException a user with username <code>user.username</code> already exists.
     */
    public User register(User user) throws InvalidUsernameException, InvalidPasswordException, UserAlreadyExistsException {
        if (user.getUsername().length() <= 0)
            throw new InvalidUsernameException();
        if (user.getPassword().length() < 4)
            throw new InvalidPasswordException();
        if (userRepository.findByUsername(user.getUsername()).isPresent())
            throw new UserAlreadyExistsException();
        return userRepository.save(user);
    }

    /**
     * Login a user with a given user.
     * @param user The user of the user to login.
     * @return The user of the logged in user.
     * @throws InvalidLoginException <code>user.username</code> and <code>user.password</code> do not make a valid login.
     */
    public User login(User user) throws InvalidLoginException {
        return userRepository.findByUsernameAndPassword(user.getUsername(), user.getPassword()).orElseThrow(() -> new InvalidLoginException());
    }

    /**
     * Get the friends of a given user.
     * @param userID The ID of the user whose friends to return.
     * @return The friends of the user.
     * @throws InvalidLoginException User with id <code>userID</code> does not exist.
     */
    public Set<User> getAllFriendsByUserID(long userID) throws InvalidUserException {
        return userRepository.findById(userID).orElseThrow(() -> new InvalidUserException(userID)).getFollowing();
    }

    /**
     * Get the followers of a given user.
     * @param userID The ID of the user whose followers to return.
     * @return The followers of the user.
     * @throws InvalidLoginException User with id <code>userID</code> does not exist.
     */
    public Set<User> getAllFollowersByUserID(long userID) throws InvalidUserException {
        return userRepository.findById(userID).orElseThrow(() -> new InvalidUserException(userID)).getFollowers();
    }

    /**
     * follow a given user.
     * @param userID The ID of the user whose followers to return.
     * @return The followers of the user.
     * @throws InvalidLoginException User with id <code>userID</code> does not exist.
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
