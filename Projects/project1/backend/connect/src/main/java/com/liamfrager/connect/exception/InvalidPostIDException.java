package com.liamfrager.connect.exception;

/**
 * Is thrown when a post with the given ID does not exist.
 */
public class InvalidPostIDException extends Exception {
    public InvalidPostIDException(long id) {
        super("A post with the given ID does not exist: " + id);
    }
}