package com.liamfrager.connect.unit_tests;
import com.liamfrager.connect.TestData;
import com.liamfrager.connect.entity.Comment;
import com.liamfrager.connect.entity.Like;
import com.liamfrager.connect.entity.Post;
import com.liamfrager.connect.entity.User;
import com.liamfrager.connect.repository.CommentRepository;
import com.liamfrager.connect.repository.LikeRepository;
import com.liamfrager.connect.repository.PostRepository;
import com.liamfrager.connect.repository.UserRepository;

import jakarta.transaction.Transactional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
public class LikeRepositoryTest {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private CommentRepository commentRepository;
    
    @Autowired
    private PostRepository postRepository;

    @Autowired
    private LikeRepository likeRepository;

    private User user;
    private Post post;
    private Comment comment;
    private Like postLike;
    private Like commentLike;

    @BeforeEach
    public void setUp() {
        userRepository.deleteAll();
        postRepository.deleteAll();
        commentRepository.deleteAll();
        user = userRepository.save(TestData.generateNewUser());
        post = postRepository.save(TestData.generateNewPost(user)); 
        comment = commentRepository.save(TestData.generateNewComment(post));
        postLike = likeRepository.save(TestData.generateNewPostLike(user, post));
        commentLike = likeRepository.save(TestData.generateNewCommentLike(user, comment));
    }

    @Test
    @Transactional
    public void testDeleteLikeById() {
        // Test for posts
        long id = postLike.getId();
        int rowsDeleted = likeRepository.deleteLikeById(id);
        assertThat(rowsDeleted).isEqualTo(1);
        assertThat(likeRepository.findById(id).isEmpty());

        // Test for comments
        id = commentLike.getId();
        rowsDeleted = likeRepository.deleteLikeById(id);
        assertThat(rowsDeleted).isEqualTo(1);
        assertThat(likeRepository.findById(id).isEmpty());
    }

    @Test
    public void testCountLikesByPostId() {
        long likeCount = likeRepository.countLikesByPostId(post.getId());

        assertThat(likeCount).isEqualTo(1);
    }

    @Test
    public void testCountLikesByCommentId() {
        long likeCount = likeRepository.countLikesByCommentId(comment.getId());

        assertThat(likeCount).isEqualTo(1);
    }

    @Test
    public void testExistsByPostIdAndUserId() {
        boolean exists = likeRepository.existsByPostIdAndUserId(post.getId(), user.getId());
        assertThat(exists).isEqualTo(true);
    }

    @Test
    public void testExistsByCommentIdAndUserId() {
        boolean exists = likeRepository.existsByCommentIdAndUserId(comment.getId(), user.getId());
        assertThat(exists).isEqualTo(true);
    }
}