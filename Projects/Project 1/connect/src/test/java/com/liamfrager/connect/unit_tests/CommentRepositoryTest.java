package com.liamfrager.connect.unit_tests;

import com.liamfrager.connect.entity.Comment;
import com.liamfrager.connect.entity.Post;
import com.liamfrager.connect.repository.CommentRepository;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.transaction.annotation.Transactional;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
public class CommentRepositoryTest {

    @Autowired
    private CommentRepository commentRepository;

    private Comment comment;

    @BeforeEach
    public void setUp() {
        comment = TestData.generateComment();
        commentRepository.save(comment);
    }

    @Test
    public void testFindAllByPostId() {
        // Assuming you have a Post entity setup in your Comment object
        Post post = TestData.generatePost();
        comment.setPost(post); // Set the postId to be used in the test

        commentRepository.save(comment);

        assertThat(commentRepository.findAllByPostId(post.getId())).isNotEmpty();
    }

    @Test
    @Transactional
    public void testDeleteCommentById() {
        long commentId = comment.getId();
        int rowsDeleted = commentRepository.deleteCommentById(commentId);

        assertThat(rowsDeleted).isEqualTo(1);
        assertThat(commentRepository.findById(commentId)).isEmpty();
    }

    @Test
    @Transactional
    public void testUpdateContentById() {
        long commentId = comment.getId();
        String newContent = "Updated content";
        int rowsUpdated = commentRepository.updateContentById(commentId, newContent);

        assertThat(rowsUpdated).isEqualTo(1);
        Comment updatedComment = commentRepository.findById(commentId).orElseThrow();
        assertThat(updatedComment.getContent()).isEqualTo(newContent);
    }
}