package com.liamfrager.connect.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.liamfrager.connect.entity.Comment;
import com.liamfrager.connect.exception.InvalidCommentContentException;
import com.liamfrager.connect.exception.InvalidCommentIDException;
import com.liamfrager.connect.exception.InvalidPostContentException;
import com.liamfrager.connect.repository.CommentRepository;

/**
 * A service for handling the <code>Comment</code> business logic.
 */
@Service
public class CommentService {
    private final CommentRepository commentRepository;

    /**
     * Constructor for the comment service.
     */
    public CommentService(CommentRepository commentRepository) {
        this.commentRepository = commentRepository;
    }
    
    /**
     * Add a new comment to the database if the comment is valid.
     * @param comment The comment to be added.
     * @return The new comment.
     * @throws InvalidPostContentException <code>comment.content</code> is either empty or longer than 255 characters.
     * @throws InvalidUserIDException There is no user with the given <code>comment.user</code> ID.
     */
    public Comment postComment(Comment comment) throws InvalidCommentContentException {
        if (comment.getContent().length() <= 0 || comment.getContent().length() >= 255)
            throw new InvalidCommentContentException(comment.getContent());
        Comment newComment = commentRepository.save(comment);
        newComment.setLikeCount(0L);
        return newComment;
    }


    /**
     * Get all comments on a post with the given ID.
     * @return A list of all the post's comments.
     */
    public List<Comment> getAllCommentsByPostID(long postID, long currentUserID) {
        return commentRepository.findAllByPostId(postID, currentUserID);
    }

    /**
     * Delete a comment with a given ID if it exists.
     * @param id The ID of the comment to delete.
     * @return The number of entries removed from the database.
     */
    public int deleteCommentByID(long commentID) {
        return commentRepository.deleteCommentById(commentID);
    }

    /**
     * Update a comment with a given ID if the given new_comment is valid.
     * @param id The ID of the comment to update.
     * @param newComment The new comment that will replace the old comment.
     * @return The updated comment.
     * @throws InvalidCommentTextException <code>new_comment.comment_content</code> is either empty or longer than 255 characters.
     * @throws InvalidCommentIDException There is no comment with the given <code>id</code>.
     */
    public int patchCommentByID(long id, Comment newComment) throws InvalidCommentContentException, InvalidCommentIDException {
        if (newComment.getContent().length() <= 0 || newComment.getContent().length() >= 255)
            throw new InvalidCommentContentException(newComment.getContent());
        int updatedRows = commentRepository.updateContentById(id, newComment.getContent());
        if (updatedRows > 0)
            return updatedRows;
        throw new InvalidCommentIDException(id);
    }
}
