package com.liamfrager.connect.exception;

/**
 * Is thrown when a given username/email already exists.
 */
public class UserAlreadyExistsException extends Exception {
    public UserAlreadyExistsException() {
        super("A user with that username/email already exists.");
    }
}
