package com.liamfrager.connect.service;


import java.util.Map;

import org.springframework.stereotype.Service;

import com.liamfrager.connect.entity.Like;
import com.liamfrager.connect.exception.InvalidCommentIDException;
import com.liamfrager.connect.exception.InvalidLikeException;
import com.liamfrager.connect.exception.InvalidPostIDException;
import com.liamfrager.connect.repository.CommentRepository;
import com.liamfrager.connect.repository.LikeRepository;
import com.liamfrager.connect.repository.PostRepository;
import com.liamfrager.connect.dto.LikeDTO;

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

    /**
     * Mark a post as liked by a user.
     * @param like The like to add.
     * @return The new like.
     * @throws InvalidLikeException
     */
    public Like postLike(Map<String, Long> likeRequest) throws InvalidLikeException, InvalidPostIDException, InvalidCommentIDException {
        Like like = new Like();
        Long postID = likeRequest.get("postID");
        Long commentID = likeRequest.get("commentID");
        if (postID != null) {
            like.setPost(postRepository.findById(postID).orElseThrow(() -> new InvalidPostIDException(postID)));
        } else if (commentID != null) {
            like.setComment(commentRepository.findById(commentID).orElseThrow(() -> new InvalidCommentIDException(commentID)));
        } else {
            throw new InvalidLikeException(like);
        }
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
