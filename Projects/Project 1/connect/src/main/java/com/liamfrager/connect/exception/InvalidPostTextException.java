package com.liamfrager.connect.exception;

/**
 * Is thrown when a post's text is either empty or longer than 255 characters.
 */
public class InvalidPostTextException extends Exception {
    public InvalidPostTextException() {
        super("The post text is either empty or longer than 255 characters.");
    }
    public InvalidPostTextException(String post) {
        super(post);
    }
}
