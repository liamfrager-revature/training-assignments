package com.liamfrager.connect.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.liamfrager.connect.entity.Post;
import com.liamfrager.connect.entity.User;
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

    public List<Post> getAllPosts(long userID) {
        return postRepository.findAll(userID);
    }

    public Post getPostByID(long id) {
        return postRepository.findById(id).orElse(null);
    }

    public Post postPost(Post post) throws InvalidPostContentException, InvalidUserException {
        if (post.getContent().length() <= 0 || post.getContent().length() >= 255)
            throw new InvalidPostContentException(post.getContent());
        User user = userRepository.findById(post.getUser().getId()).orElseThrow(() -> new InvalidUserException(post.getUser().getId()));
        post.setUser(user);
        Post newPost = postRepository.save(post);
        newPost.setLikeCount(0L);
        newPost.setCommentCount(0L);
        return newPost;
    }

    public int updatePostByID(long id, Post newPost) throws InvalidPostContentException, InvalidPostIDException {
        if (newPost.getContent().length() <= 0 || newPost.getContent().length() >= 255)
            throw new InvalidPostContentException(newPost.getContent());
        int updatedRows = postRepository.updateContentById(id, newPost.getContent());
        if (updatedRows > 0)
            return updatedRows;
        throw new InvalidPostIDException(id);
    }

    public int deletePostByID(long id) {
        return postRepository.deletePostById(id);
    }
    
    public List<Post> getAllPostsByUserID(long id) throws InvalidUserException {
        if (!userRepository.existsById(id)) throw new InvalidUserException(id);
        return postRepository.findByUserId(id);
    }
}
