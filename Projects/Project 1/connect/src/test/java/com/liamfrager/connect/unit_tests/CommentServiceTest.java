package com.liamfrager.connect.unit_tests;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.List;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.liamfrager.connect.entity.Comment;
import com.liamfrager.connect.exception.InvalidCommentContentException;
import com.liamfrager.connect.exception.InvalidUserException;
import com.liamfrager.connect.repository.CommentRepository;
import com.liamfrager.connect.repository.UserRepository;
import com.liamfrager.connect.service.CommentService;

@ExtendWith(MockitoExtension.class)
public class CommentServiceTest {
    @Mock
    private CommentRepository commentRepository;
    
    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private CommentService commentService;

    @Test
    void postComment_ShouldSaveComment_WhenValid() throws InvalidCommentContentException, InvalidUserException {
        Comment comment = TestData.generateComment();
        when(userRepository.existsById(comment.getUser().getId())).thenReturn(true);
        when(commentRepository.save(comment)).thenReturn(comment);

        Comment result = commentService.postComment(comment);

        assertEquals(comment, result);
        verify(commentRepository, times(1)).save(comment);
    }

    @Test
    void postComment_ShouldThrowException_WhenInvalidContent() {
        Comment comment = TestData.generateComment();

        assertThrows(InvalidCommentContentException.class, () -> commentService.postComment(comment));
    }

    @Test
    void deleteCommentByID_ShouldReturnNumberOfDeletedRows() {
        when(commentRepository.deleteCommentById(1L)).thenReturn(1);

        int deletedRows = commentService.deleteCommentByID(1L);

        assertEquals(1, deletedRows);
    }

    @Test
    void getAllCommentsByPostID_ShouldReturnCommentList() {
        List<Comment> comments = List.of(TestData.generateComment());
        when(commentRepository.findAllByPostId(1L)).thenReturn(comments);

        List<Comment> result = commentService.getAllCommentsByPostID(1L);

        assertEquals(comments, result);
    }
}
