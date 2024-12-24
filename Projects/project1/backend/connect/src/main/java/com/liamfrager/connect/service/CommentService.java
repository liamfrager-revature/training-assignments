package com.liamfrager.connect.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.liamfrager.connect.entity.Comment;
import com.liamfrager.connect.exception.InvalidCommentContentException;
import com.liamfrager.connect.exception.InvalidCommentIDException;
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
    
    public Comment postComment(Comment comment) throws InvalidCommentContentException {
        if (comment.getContent().length() <= 0 || comment.getContent().length() >= 255)
            throw new InvalidCommentContentException(comment.getContent());
        Comment newComment = commentRepository.save(comment);
        newComment.setLikeCount(0L);
        return newComment;
    }

    public List<Comment> getAllCommentsByPostID(long postID, long currentUserID) {
        return commentRepository.findAllByPostId(postID, currentUserID);
    }

    public int deleteCommentByID(long commentID) {
        return commentRepository.deleteCommentById(commentID);
    }

    public int updateCommentByID(long id, Comment newComment) throws InvalidCommentContentException, InvalidCommentIDException {
        if (newComment.getContent().length() <= 0 || newComment.getContent().length() >= 255)
            throw new InvalidCommentContentException(newComment.getContent());
        int updatedRows = commentRepository.updateContentById(id, newComment.getContent());
        if (updatedRows > 0)
            return updatedRows;
        throw new InvalidCommentIDException(id);
    }
}
