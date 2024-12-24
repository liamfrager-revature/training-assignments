package com.liamfrager.connect.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import com.liamfrager.connect.TestData;
import com.liamfrager.connect.entity.Comment;
import com.liamfrager.connect.exception.InvalidCommentContentException;
import com.liamfrager.connect.repository.CommentRepository;

@ExtendWith(MockitoExtension.class)
public class CommentServiceTest {
    @InjectMocks
    private CommentService commentService;

    @Mock
    private CommentRepository commentRepository;

    @Test
    void testPostCommentSuccess() throws Exception {
        Comment newComment = TestData.generateNewComment(TestData.generatePost());
        Mockito.when(commentRepository.save(any(Comment.class))).thenReturn(newComment);

        Comment postedComment = commentService.postComment(newComment);

        assertEquals(newComment.getContent(), postedComment.getContent());
    }

    @Test
    void testPostCommentInvalidContent() {
        Comment invalidComment = TestData.generateNewComment(TestData.generatePost());
        invalidComment.setContent("");

        assertThrows(InvalidCommentContentException.class, () -> commentService.postComment(invalidComment));
    }

    @Test
    void testDeleteCommentSuccess() {
        Mockito.when(commentRepository.deleteCommentById(1L)).thenReturn(1);

        int deletedCount = commentService.deleteCommentByID(1L);

        assertEquals(1, deletedCount);
    }
}