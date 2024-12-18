package com.liamfrager.connect.exception;

/**
 * Is thrown when the user attempts to follow themselves or a user they already follow.
 */
public class InvalidFollowException extends Exception {
    public InvalidFollowException(Long userID) {
        super("Cannot follow " + userID);
    }
}
