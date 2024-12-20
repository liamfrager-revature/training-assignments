package com.liamfrager.connect.unit_tests;


import com.liamfrager.connect.TestData;
import com.liamfrager.connect.entity.Comment;
import com.liamfrager.connect.entity.Post;
import com.liamfrager.connect.entity.User;
import com.liamfrager.connect.repository.CommentRepository;
import com.liamfrager.connect.repository.PostRepository;
import com.liamfrager.connect.repository.UserRepository;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@Transactional(propagation = Propagation .NOT_SUPPORTED)
public class CommentRepositoryTest {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private CommentRepository commentRepository;
    
    @Autowired
    private PostRepository postRepository;

    private User user;
    private Post post;
    private Comment comment;

    @BeforeEach
    public void setUp() {
        userRepository.deleteAll();
        postRepository.deleteAll();
        commentRepository.deleteAll();
        user = userRepository.save(TestData.generateNewUser());
        post = postRepository.save(TestData.generateNewPost(user)); 
        comment = commentRepository.save(TestData.generateNewComment(post));
    }

    @Test
    public void testFindAllByPostId() {
        System.err.println("Starting test");
        assertThat(commentRepository.findAllByPostId(post.getId(), 1L)).isNotEmpty();
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