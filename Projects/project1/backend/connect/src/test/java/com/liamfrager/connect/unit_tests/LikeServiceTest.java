package com.liamfrager.connect.unit_tests;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.liamfrager.connect.TestData;
import com.liamfrager.connect.entity.Like;
import com.liamfrager.connect.dto.LikeDTO;
import com.liamfrager.connect.exception.InvalidLikeException;
import com.liamfrager.connect.repository.LikeRepository;
import com.liamfrager.connect.service.LikeService;

@ExtendWith(MockitoExtension.class)
public class LikeServiceTest {
    @Mock
    private LikeRepository likeRepository;

    @InjectMocks
    private LikeService likeService;

    @Test
    void postPostLike_ShouldSaveLike_WhenValid() throws InvalidLikeException {
        Like like = TestData.generatePostLike();
        when(likeRepository.save(like)).thenReturn(like);

        Like result = likeService.postLike(like);

        assertEquals(like, result);
        verify(likeRepository, times(1)).save(like);
    }

    @Test
    void postCommentLike_ShouldSaveLike_WhenValid() throws InvalidLikeException {
        Like like = TestData.generateCommentLike();
        when(likeRepository.save(like)).thenReturn(like);

        Like result = likeService.postLike(like);

        assertEquals(like, result);
        verify(likeRepository, times(1)).save(like);
    }

    @Test
    void deleteLike_ShouldThrowException_WhenLikeDoesNotExist() {
        when(likeRepository.findById(1L)).thenReturn(Optional.empty());

        assertThrows(InvalidLikeException.class, () -> likeService.deleteLike(1L));
    }

    @Test
    void getLikesFromPost_ShouldReturnLikes() {
        when(likeRepository.countLikesByPostId(1L)).thenReturn(10L);
        when(likeRepository.existsByPostIdAndUserId(1L, 1L)).thenReturn(false);

        LikeDTO response = likeService.getLikesFromPost(1L);

        assertEquals(10, response.getLikesCount());
    }
}
