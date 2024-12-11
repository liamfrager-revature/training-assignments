package com.liamfrager.connect.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.liamfrager.connect.entity.Post;

/**
 * A JPA repository for posts.
 */
@Repository
public interface PostRepository extends JpaRepository<Post, Integer> {

    /**
     * Get all posts posted by the user with the given ID.
     * @param postedBy The ID of the user whose posts will be returned.
     * @return A list of <code>Post</code>s posted by the given user.
     */
    List<Post> findByPostedBy(int postedBy);
    
    /**
     * Delete a post with the given ID.
     * @param postID The ID of the post to delete.
     * @return The number of entries updated.
     */
    @Modifying
    @Transactional
    @Query("DELETE FROM Post WHERE postId = ?1")
    int deletePostByPostId(int postID);

    /**
     * Update the post text of the post with the given ID.
     * @param postID The ID of the post to update.
     * @param postText The new text to update the post with.
     * @return The number of entries updated.
     */
    @Modifying
    @Transactional
    @Query("UPDATE Post SET postText = ?2 WHERE postId = ?1")
    int updatePostTextById(int postID, String postText);
}
