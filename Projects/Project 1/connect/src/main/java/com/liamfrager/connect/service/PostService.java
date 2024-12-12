package com.liamfrager.connect.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.liamfrager.connect.entity.Post;
import com.liamfrager.connect.exception.*;
import com.liamfrager.connect.repository.UserRepository;
import com.liamfrager.connect.repository.PostRepository;

/**
 * A service for handling the <code>Post</code> business logic.
 */
@Service
public class PostService {
    private final PostRepository postRepository;
    private final UserRepository userRepository;

    /**
     * Constructor for the post service.
     */
    public PostService(PostRepository postRepository, UserRepository userRepository) {
        this.postRepository = postRepository;
        this.userRepository = userRepository;
    }

    /**
     * Get all posts.
     * @return A list of all posts.
     */
    public List<Post> getAllPosts() {
        return postRepository.findAll();
    }
    
    /**
     * Get a post with a given ID.
     * @param id The ID of the post to be returned.
     * @return The post with the given <code>id</code>.
     */
    public Post getPostByID(int id) {
        return postRepository.findById(id).orElse(null);
    }
    
    /**
     * Add a new post to the database if the post is valid.
     * @param post The post to be added.
     * @return The new post.
     * @throws InvalidPostTextException <code>post.post_text</code> is either empty or longer than 255 characters.
     * @throws InvalidUserIDException There is no user with the given <code>post.posted_by</code> ID.
     */
    public Post postPost(Post post) throws InvalidPostTextException, InvalidUserIDException {
        if (post.getContent().length() <= 0 || post.getContent().length() >= 255)
            throw new InvalidPostTextException();
        if (userRepository.findById(post.getUser().getId()).isEmpty())
            throw new InvalidUserIDException(post.getUser().getId());
        return postRepository.save(post);
        
    }
    
    /**
     * Update a post with a given ID if the given new_post is valid.
     * @param id The ID of the post to update.
     * @param newPost The new post that will replace the old post.
     * @return The updated post.
     * @throws InvalidPostTextException <code>new_post.post_text</code> is either empty or longer than 255 characters.
     * @throws InvalidPostIDException There is no post with the given <code>id</code>.
     */
    public int patchPostByID(int id, Post newPost) throws InvalidPostTextException, InvalidPostIDException {
        if (newPost.getContent().length() <= 0 || newPost.getContent().length() >= 255)
            throw new InvalidPostTextException();
        int updatedRows = postRepository.updateContentById(id, newPost.getContent());
        if (updatedRows > 0)
            return updatedRows;
        throw new InvalidPostIDException();
    }
    
    /**
     * Delete a post with a given ID if it exists.
     * @param id The ID of the post to delete.
     * @return The number of entries removed from the database.
     */
    public int deletePostByID(int id) {
        return postRepository.deletePostById(id);
    }
    
    /**
     * Get all posts posted by a user with the given ID.
     * @param id The ID of the user whose posts will be returned. Returns null if the ID is invalid
     * @return A list of all posts by the user with the given <code>id</code>.
     */
    public List<Post> getAllPostsByUserID(int id) {
        return postRepository.findByUserId(id);
    }
}
