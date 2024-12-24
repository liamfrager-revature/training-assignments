package com.liamfrager.connect.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;

import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import com.liamfrager.connect.TestData;
import com.liamfrager.connect.entity.Post;
import com.liamfrager.connect.entity.User;
import com.liamfrager.connect.exception.InvalidPostContentException;
import com.liamfrager.connect.repository.PostRepository;
import com.liamfrager.connect.repository.UserRepository;

@ExtendWith(MockitoExtension.class)
public class PostServiceTest {
    @InjectMocks
    private PostService postService;

    @Mock
    private PostRepository postRepository;

    @Mock
    private UserRepository userRepository;

    @Test
    void testPostPostSuccess() throws Exception {
        User user = TestData.generateUser();
        Post newPost = TestData.generateNewPost(user);
        Mockito.when(userRepository.findById(user.getId())).thenReturn(Optional.of(user));
        Mockito.when(postRepository.save(any(Post.class))).thenReturn(newPost);

        Post postedPost = postService.postPost(newPost);
        assertEquals(newPost, postedPost);
    }

    @Test
    void testPostPostInvalidContent() {
        User user = TestData.generateUser();
        Post invalidPost = TestData.generateNewPost(user);
        invalidPost.setContent("");

        assertThrows(InvalidPostContentException.class, () -> postService.postPost(invalidPost));
    }
}