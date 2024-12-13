package com.liamfrager.connect.exception;

/**
 * Is thrown when the given post content is invalid.
 */
public class InvalidPostContentException extends Exception {
    public InvalidPostContentException(String content) {
        super("The content of this post is invalid: " + content);
    }
}