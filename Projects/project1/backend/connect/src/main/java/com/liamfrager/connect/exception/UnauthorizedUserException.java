package com.liamfrager.connect.exception;

/**
 * Is thrown when the given comment content is invalid.
 */
public class UnauthorizedUserException extends Exception {
    public UnauthorizedUserException() {
        super("This request is unauthorized.");
    }
}