package com.liamfrager.connect.exception;

/**
 * Is thrown when a like is associated with both a comment and a post, or neither.
 */
public class InvalidLikeException extends Exception {
    public InvalidLikeException(long likeID) {
        super("The Like with ID " + likeID + "does not exist.");
    }
}
