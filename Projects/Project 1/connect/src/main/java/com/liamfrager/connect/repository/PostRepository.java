package com.liamfrager.connect.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.liamfrager.connect.entity.Post;

/**
 * A JPA repository for posts.
 */
@Repository
public interface PostRepository extends JpaRepository<Post, Long> {

    /**
     * Get all posts posted by the user with the given ID.
     * @param postedBy The user whose posts will be returned.
     * @return A list of <code>Post</code>s posted by the given user.
     */
    public List<Post> findByUserId(long id);
    
    /**
     * Delete a post with the given ID.
     * @param postID The ID of the post to delete.
     * @return The number of entries updated.
     */
    @Modifying
    @Transactional
    @Query("DELETE FROM Post WHERE id = :postID")
    public int deletePostById(@Param("postID") long postID);

    /**
     * Update the post text of the post with the given ID.
     * @param postID The ID of the post to update.
     * @param postContent The new content to update the post with.
     * @return The number of entries updated.
     */
    @Modifying
    @Transactional
    @Query("UPDATE Post SET content = :postContent WHERE id = :postID")
    public int updateContentById(@Param("postID") long postID, @Param("postContent") String postContent);
}
