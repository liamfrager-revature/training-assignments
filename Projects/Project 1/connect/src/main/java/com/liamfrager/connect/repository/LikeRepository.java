package com.liamfrager.connect.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

import com.liamfrager.connect.entity.Like;

/**
 * A JPA repository for likes.
 */
public interface LikeRepository extends JpaRepository<Like, Long> {
    /**
     * Delete a like with the given ID.
     * @param likeID The ID of the like to delete.
     * @return The number of entries updated.
     */
    @Modifying
    @Transactional
    @Query("DELETE FROM Like WHERE id = ?1")
    public int deleteLikeById(long likeID);

    @Query("SELECT COUNT(l) FROM Like l WHERE l.post.id = ?1")
    public long countLikesByPostId(long postId);

    @Query("SELECT COUNT(l) FROM Like l WHERE l.comment.id = ?1")
    public long countLikesByCommentId(long commentId);

    @Query("SELECT l FROM Like WHERE l.post.id = ?1 AND l.user.id = ?2")
    public Like findByPostIdAndUserId(long postID, long userId);

    @Query("SELECT l FROM Like WHERE l.comment.id = ?1 AND l.user.id = ?2")
    public Like findByCommentIdAndUserId(long commentID, long userID);
}
