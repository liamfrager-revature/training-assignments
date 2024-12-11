package com.liamfrager.connect.exception;

/**
 * Is thrown when there is no post with a given ID.
 */
public class InvalidPostIDException extends Exception {
    public InvalidPostIDException() {
        super("A post with that ID does not exist.");
    }
    public InvalidPostIDException(String post) {
        super(post);
    }
}
