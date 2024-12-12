package com.liamfrager.connect.service;

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
        // If the username and password provided in the request body JSON match a real user existing on the database.
        return userRepository.findByUsernameAndPassword(user.getUsername(), user.getPassword()).orElseThrow(() -> new InvalidLoginException());
    }
}
