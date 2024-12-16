package com.liamfrager.connect.exception;

/**
 * Is thrown when a comment with the given ID does not exist.
 */
public class InvalidCommentIDException extends Exception {
    public InvalidCommentIDException(long id) {
        super("A comment with the given ID does not exist: " + id);
    }
}