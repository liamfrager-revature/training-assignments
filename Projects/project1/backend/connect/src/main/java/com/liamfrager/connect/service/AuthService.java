package com.liamfrager.connect.service;

import com.liamfrager.connect.entity.User;
import com.liamfrager.connect.exception.*;
import com.liamfrager.connect.repository.UserRepository;
import com.liamfrager.connect.AuthUtil;

import org.springframework.stereotype.Service;

/**
 * A service for handling the authentication business logic.
 */
@Service
public class AuthService {
    private final UserRepository userRepository;

    /**
     * Constructor for the auth service.
     */
    public AuthService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public User register(User user) throws InvalidUsernameException, InvalidPasswordException, UserAlreadyExistsException {
        if (user.getUsername().length() <= 0)
            throw new InvalidUsernameException();
        if (user.getPassword().length() < 8)
            throw new InvalidPasswordException();
        if (userRepository.findByUsername(user.getUsername()).isPresent() || userRepository.findByEmail(user.getEmail()).isPresent())
            throw new UserAlreadyExistsException();

        user.setPassword(AuthUtil.hashPassword(user.getPassword()));
        return userRepository.save(user);
    }

    public User login(User user) throws InvalidLoginException {
        User userData = userRepository.findByUsername(user.getUsername())
            .or(() -> userRepository.findByEmail(user.getEmail()))
            .orElseThrow(() -> new InvalidLoginException());
        boolean isValidLogin = AuthUtil.checkPassword(user.getPassword(), userData.getPassword());
        if (isValidLogin)
            return userData;
        throw new InvalidLoginException();
    }
}