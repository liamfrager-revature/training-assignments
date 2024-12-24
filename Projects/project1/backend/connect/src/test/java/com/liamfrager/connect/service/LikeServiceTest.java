package com.liamfrager.connect.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;

import java.util.Map;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import com.liamfrager.connect.TestData;
import com.liamfrager.connect.entity.Like;
import com.liamfrager.connect.entity.Post;
import com.liamfrager.connect.entity.User;
import com.liamfrager.connect.repository.CommentRepository;
import com.liamfrager.connect.repository.LikeRepository;
import com.liamfrager.connect.repository.PostRepository;

@ExtendWith(MockitoExtension.class)
public class LikeServiceTest {
    @InjectMocks
    private LikeService likeService;

    @Mock
    private LikeRepository likeRepository;

    @Mock
    private PostRepository postRepository;

    @Mock
    private CommentRepository commentRepository;

    @Test
    void testPostLikeSuccess() throws Exception {
        User user = TestData.generateUser();
        Post post = TestData.generatePost();
        Like expectedLike = TestData.generateNewPostLike(user, post);
        Mockito.when(postRepository.findById(post.getId())).thenReturn(Optional.of(post));
        Mockito.when(postRepository.findById(post.getId())).thenReturn(Optional.of(post));
        Mockito.when(likeRepository.save(any(Like.class))).thenReturn(expectedLike);

        Map<String, Long> likeRequest = Map.of("postID", user.getId());
        Like actualLike = likeService.postLike(likeRequest, user);

        assertNotNull(actualLike);
        assertEquals(expectedLike, actualLike);
    }

    @Test
    void testDeleteLikeSuccess() throws Exception {
        Like like = TestData.generatePostLike();
        Mockito.when(likeRepository.findById(like.getId())).thenReturn(Optional.of(like));
        Mockito.when(likeRepository.deleteLikeById(like.getId())).thenReturn(1);
        int rowsDeleted = likeService.deleteLike(like.getId(), like.getUser().getId());

        assertEquals(1, rowsDeleted);
    }
}
