package com.liamfrager.connect.exception;

import com.liamfrager.connect.entity.User;

/**
 * Is thrown when the given user does not exists.
 */
public class InvalidUserException extends Exception {
    public InvalidUserException(User user) {
        super("The user does not exist: " + user.toString());
    }
    public InvalidUserException(long id) {
        super("The user with the given ID does not exist: " + id);
    }
}
