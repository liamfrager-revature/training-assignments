package com.liamfrager.connect.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import com.liamfrager.connect.entity.Like;

/**
 * A JPA repository for likes.
 */
public interface LikeRepository extends JpaRepository<Like, Long> {
    
    @Modifying
    @Transactional
    @Query("DELETE FROM Like WHERE id = :likeID")
    public int deleteLikeById(@Param("likeID") long likeID);

    @Query("SELECT COUNT(l) FROM Like l WHERE l.post.id = :postID")
    public long countLikesByPostId(@Param("postID") long postID);

    @Query("SELECT COUNT(l) FROM Like l WHERE l.comment.id = :commentID")
    public long countLikesByCommentId(@Param("commentID") long commentId);

    @Query("SELECT CASE WHEN COUNT(l) > 0 THEN TRUE ELSE FALSE END FROM Like l WHERE l.post.id = :postID AND l.user.id = :userID")
    public boolean existsByPostIdAndUserId(@Param("postID") long postID, @Param("userID") long userID);

    @Query("SELECT CASE WHEN COUNT(l) > 0 THEN TRUE ELSE FALSE END FROM Like l WHERE l.comment.id = :commentID AND l.user.id = :userID")
    public boolean existsByCommentIdAndUserId(@Param("commentID") long commentID, @Param("userID") long userID);
}
