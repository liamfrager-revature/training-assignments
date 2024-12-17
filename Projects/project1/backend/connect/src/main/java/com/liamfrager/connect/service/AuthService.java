package com.liamfrager.connect.service;

import com.liamfrager.connect.entity.User;
import com.liamfrager.connect.exception.*;
import com.liamfrager.connect.repository.UserRepository;
import com.liamfrager.connect.AuthUtil;

import org.springframework.stereotype.Service;

@Service
public class AuthService {
    private final UserRepository userRepository;

    public AuthService(UserRepository userRepository) {
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

        user.setPassword(AuthUtil.hashPassword(user.getPassword()));
        return userRepository.save(user);
    }

    /**
     * Login a user with a given user.
     * @param user The user of the user to login.
     * @return The user of the logged in user.
     * @throws InvalidLoginException <code>user.username</code> and <code>user.password</code> do not make a valid login.
     */
    public User login(User user) throws InvalidLoginException {
        User userData = userRepository.findByUsername(user.getUsername())
            .or(() -> userRepository.findByEmail(user.getEmail()))
            .orElseThrow(() -> new InvalidLoginException());
        boolean isValidLogin = AuthUtil.checkPassword(user.getPassword(), userData.getPassword());
        if (isValidLogin) {
            userData.setPassword(null);
            return userData;
        }
        throw new InvalidLoginException();
    }
}