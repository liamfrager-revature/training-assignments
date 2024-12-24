package com.liamfrager.connect.service;


import java.util.Map;

import org.springframework.stereotype.Service;

import com.liamfrager.connect.entity.Like;
import com.liamfrager.connect.entity.User;
import com.liamfrager.connect.exception.InvalidCommentIDException;
import com.liamfrager.connect.exception.InvalidLikeException;
import com.liamfrager.connect.exception.InvalidPostIDException;
import com.liamfrager.connect.exception.UnauthorizedUserException;
import com.liamfrager.connect.repository.CommentRepository;
import com.liamfrager.connect.repository.LikeRepository;
import com.liamfrager.connect.repository.PostRepository;

/**
 * A service for handling the <code>Like</code> business logic.
 */
@Service
public class LikeService {
    private final LikeRepository likeRepository;
    private final PostRepository postRepository;
    private final CommentRepository commentRepository;

    /**
     * Constructor for the like service.
     */
    public LikeService(LikeRepository likeRepository, PostRepository postRepository, CommentRepository commentRepository) {
        this.likeRepository = likeRepository;
        this.postRepository = postRepository;
        this.commentRepository = commentRepository;
    }

    public Like postLike(Map<String, Long> likeRequest, User currentUser) throws InvalidLikeException, InvalidPostIDException, InvalidCommentIDException {
        Like like = new Like();
        Long postID = likeRequest.get("postID");
        Long commentID = likeRequest.get("commentID");
        if (postID != null) {
            if (likeRepository.existsByPostIdAndUserId(postID, currentUser.getId()))
                throw new InvalidLikeException(like.getId());
            like.setPost(postRepository.findById(postID).orElseThrow(() -> new InvalidPostIDException(postID)));
        } else if (commentID != null) {
            if (likeRepository.existsByCommentIdAndUserId(commentID, currentUser.getId()))
                throw new InvalidLikeException(like.getId());
            like.setComment(commentRepository.findById(commentID).orElseThrow(() -> new InvalidCommentIDException(commentID)));
        } else {
            throw new InvalidLikeException(like.getId());
        }
        like.setUser(currentUser);

        return likeRepository.save(like);
    }

    public int deleteLike(long likeID, long userID) throws InvalidLikeException, UnauthorizedUserException {
        Like like = likeRepository.findById(likeID)
            .orElseThrow(() -> new InvalidLikeException(likeID));
        if (like.getUser().getId() != userID)
            throw new UnauthorizedUserException();
        return likeRepository.deleteLikeById(likeID);
    }
}
