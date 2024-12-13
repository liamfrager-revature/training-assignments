package com.liamfrager.connect.exception;

import com.liamfrager.connect.entity.Like;

/**
 * Is thrown when a like is associated with both a comment and a post, or neither.
 */
public class InvalidLikeException extends Exception {
    public InvalidLikeException(Like like) {
        super("The like with ID " + like.getId() + " is invlid: \n" + like.getPost().getId() + "\n" + like.getComment().getId());
    }
    public InvalidLikeException(long likeID) {
        super("The Like with ID " + likeID + "does not exist.");
    }
}
