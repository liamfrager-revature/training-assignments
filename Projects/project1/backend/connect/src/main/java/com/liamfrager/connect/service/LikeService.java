package com.liamfrager.connect.service;


import org.springframework.stereotype.Service;

import com.liamfrager.connect.entity.Like;
import com.liamfrager.connect.exception.InvalidLikeException;
import com.liamfrager.connect.repository.LikeRepository;
import main.java.com.liamfrager.connect.dto.LikeDTO;

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

    public LikeDTO getLikesFromPost(long postID) {
        return new LikeDTO(
            likeRepository.countLikesByPostId(postID),
            likeRepository.existsByPostIdAndUserId(postID, 1L) // TODO: get current user id
        );
    }

    public LikeDTO getLikesFromComment(long commentID) {
        return new LikeDTO(
            likeRepository.countLikesByCommentId(commentID),
            likeRepository.existsByCommentIdAndUserId(commentID, 1L) // TODO: get current user id
        );
    }
}
