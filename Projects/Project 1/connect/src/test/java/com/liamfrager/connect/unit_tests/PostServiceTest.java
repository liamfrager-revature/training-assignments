package com.liamfrager.connect.unit_tests;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;

import java.time.LocalDateTime;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.liamfrager.connect.entity.Post;
import com.liamfrager.connect.entity.User;
import com.liamfrager.connect.exception.InvalidPostContentException;
import com.liamfrager.connect.exception.InvalidPostIDException;
import com.liamfrager.connect.exception.InvalidUserException;
import com.liamfrager.connect.repository.PostRepository;
import com.liamfrager.connect.repository.UserRepository;
import com.liamfrager.connect.service.PostService;

@ExtendWith(MockitoExtension.class)
public class PostServiceTest {
    @Mock
    private PostRepository postRepository;

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private PostService postService;

    @Test
    void getAllPosts_ShouldReturnListOfPosts() {
        List<Post> posts = List.of(TestData.generatePost());
        when(postRepository.findAll()).thenReturn(posts);

        List<Post> result = postService.getAllPosts();

        assertEquals(posts, result);
    }

    @Test
    void postPost_ShouldSavePost_WhenValid() throws InvalidPostContentException, InvalidUserException {
        Post post = TestData.generatePost();
        when(userRepository.existsById(1L)).thenReturn(true);
        when(postRepository.save(post)).thenReturn(post);

        Post result = postService.postPost(post);

        assertEquals(post, result);
    }

    @Test
    void patchPostByID_ShouldThrowException_WhenPostNotFound() {
        when(postRepository.updateContentById(1L, "New Content")).thenReturn(0);

        assertThrows(InvalidPostIDException.class, () -> postService.patchPostByID(1L, new Post(1L, new User(1L, "username", "test@email.com", "password"), "Content", LocalDateTime.now())));
    }
}