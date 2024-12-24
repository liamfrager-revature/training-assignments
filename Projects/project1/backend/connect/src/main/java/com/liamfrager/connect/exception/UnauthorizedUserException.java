package com.liamfrager.connect.exception;

/**
 * Is thrown when the user is unauthorized to perform an action.
 */
public class UnauthorizedUserException extends Exception {
    public UnauthorizedUserException() {
        super("This request is unauthorized.");
    }
}