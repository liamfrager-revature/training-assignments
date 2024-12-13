package com.liamfrager.connect.exception;

/**
 * Is thrown when the given comment content is invalid.
 */
public class InvalidCommentContentException extends Exception {
    public InvalidCommentContentException(String content) {
        super("The content of this comment is invalid: " + content);
    }
}