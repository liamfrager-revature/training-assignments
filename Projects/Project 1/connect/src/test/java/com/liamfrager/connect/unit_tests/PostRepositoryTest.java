package com.liamfrager.connect.unit_tests;

import com.liamfrager.connect.entity.Post;
import com.liamfrager.connect.entity.User;
import com.liamfrager.connect.repository.PostRepository;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.transaction.annotation.Transactional;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
public class PostRepositoryTest {

    @Autowired
    private PostRepository postRepository;

    private Post post;

    @BeforeEach
    public void setUp() {
        post = TestData.generatePost();
        postRepository.save(post);
    }

    @Test
    public void testFindByUserId() {
        User user = TestData.generateUser();
        post.setUser(user); // Assuming User entity is set properly

        postRepository.save(post);

        assertThat(postRepository.findByUserId(user.getId())).isNotEmpty();
    }

    @Test
    @Transactional
    public void testDeletePostById() {
        long postId = post.getId();
        int rowsDeleted = postRepository.deletePostById(postId);

        assertThat(rowsDeleted).isEqualTo(1);
        assertThat(postRepository.findById(postId)).isEmpty();
    }

    @Test
    @Transactional
    public void testUpdateContentById() {
        long postId = post.getId();
        String newContent = "Updated post content";
        int rowsUpdated = postRepository.updateContentById(postId, newContent);

        assertThat(rowsUpdated).isEqualTo(1);
        Post updatedPost = postRepository.findById(postId).orElseThrow();
        assertThat(updatedPost.getContent()).isEqualTo(newContent);
    }
}