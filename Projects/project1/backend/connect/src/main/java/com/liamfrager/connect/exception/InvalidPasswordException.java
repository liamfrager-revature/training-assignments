package com.liamfrager.connect.exception;

/**
 * Is thrown when a given password is less than 8 characters long.
 */
public class InvalidPasswordException extends Exception {
    public InvalidPasswordException() {
        super("The given password is too short.");
    }
}