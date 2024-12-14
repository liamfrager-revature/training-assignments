package com.liamfrager.connect.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.liamfrager.connect.entity.Comment;

/**
 * A JPA repository for comments.
 */
@Repository
public interface CommentRepository extends JpaRepository<Comment, Long> {
    public List<Comment> findAllByPostId(long postID);

    /**
     * Delete a comment with the given ID.
     * @param commentID The ID of the comment to delete.
     * @return The number of entries updated.
     */
    @Modifying
    @Transactional
    @Query("DELETE FROM Comment WHERE id = :commentID")
    public int deleteCommentById(@Param("commentID") long commentID);

    /**
     * Update the comment text of the comment with the given ID.
     * @param commentID The ID of the comment to update.
     * @param commentContent The new content to update the comment with.
     * @return The number of entries updated.
     */
    @Modifying
    @Transactional
    @Query("UPDATE Comment SET content = :commentContent WHERE id = :commentID")
    public int updateContentById(@Param("commentID") long commentID, @Param("commentContent") String commentContent);
}
