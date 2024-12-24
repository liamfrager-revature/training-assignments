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

    @Query("SELECT p FROM Post p WHERE p.user.id = :userID ORDER BY p.timestamp DESC")
    public List<Post> findByUserId(@Param("userID") long userID);
    
    @Modifying
    @Transactional
    @Query("DELETE FROM Post WHERE id = :postID")
    public int deletePostById(@Param("postID") long postID);

    @Modifying
    @Transactional
    @Query("UPDATE Post SET content = :postContent WHERE id = :postID")
    public int updateContentById(@Param("postID") long postID, @Param("postContent") String postContent);

    @Query("SELECT new com.liamfrager.connect.entity.Post(p, l.id) FROM Post p LEFT JOIN Like l ON p.id = l.post.id AND l.user.id = :currentUserID WHERE p.user.id IN (SELECT f.followee.id FROM Follow f WHERE f.follower.id = :currentUserID) ORDER BY p.timestamp DESC")
    List<Post> findAll(@Param("currentUserID") Long currentUserID);

    @Query("SELECT p FROM Post p WHERE LOWER(p.content) LIKE LOWER(CONCAT('%', :query, '%')) AND p.user.id != :currentUserID")
    List<Post> searchByContentExcludingUser(@Param("query") String query, @Param("currentUserID") Long currentUserID);
}
