package com.liamfrager.connect.service;


import org.springframework.stereotype.Service;

import com.liamfrager.connect.entity.Like;
import com.liamfrager.connect.entity.LikeResponse;
import com.liamfrager.connect.exception.InvalidLikeException;
import com.liamfrager.connect.repository.LikeRepository;

/**
 * A service for handling the <code>Like</code> business logic.
 */
@Service
public class LikeService {
    private final LikeRepository likeRepository;

    /**
     * Constructor for the like service.
     */
    public LikeService(LikeRepository likeRepository) {
        this.likeRepository = likeRepository;
    }

    /**
     * Mark a post as liked by a user.
     * @param like The like to add.
     * @return The new like.
     * @throws InvalidLikeException
     */
    public Like postLike(Like like) throws InvalidLikeException {
        if ((like.getPost() != null && like.getComment() != null) || (like.getPost() == null && like.getComment() == null))
            throw new InvalidLikeException(like);
        return likeRepository.save(like);
    }

    /**
     * Mark a post as unliked by a user.
     * @param likeID The ID of the like to remove.
     * @throws InvalidLikeException
     */
    public int deleteLike(long likeID) throws InvalidLikeException {
            likeRepository.findById(likeID)
            .orElseThrow(() -> new InvalidLikeException(likeID));
        return likeRepository.deleteLikeById(likeID);
    }

    public LikeResponse getLikeResponseFromPost(long postID) {
        return new LikeResponse(
            likeRepository.countLikesByPostId(postID),
            likeRepository.findByPostIdAndUserId(postID, 1L) // TODO: get current user id
        );
    }

    public LikeResponse getLikeResponseFromComment(long commentID) {
        return new LikeResponse(
            likeRepository.countLikesByCommentId(commentID),
            likeRepository.findByCommentIdAndUserId(commentID, 1L) // TODO: get current user id
        );
    }
}
