package com.liamfrager.connect.unit_tests;
import com.liamfrager.connect.entity.Like;
import com.liamfrager.connect.repository.LikeRepository;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.transaction.annotation.Transactional;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
public class LikeRepositoryTest {

    @Autowired
    private LikeRepository likeRepository;

    private Like like;

    @BeforeEach
    public void setUp() {
        like = TestData.generatePostLike();
        // Set properties of Like (post, user, etc.)
        likeRepository.save(like);
    }

    @Test
    @Transactional
    public void testDeleteLikeById() {
        long likeId = like.getId();
        int rowsDeleted = likeRepository.deleteLikeById(likeId);

        assertThat(rowsDeleted).isEqualTo(1);
        assertThat(likeRepository.findById(likeId)).isEmpty();
    }

    @Test
    public void testCountLikesByPostId() {
        long postId = 1L;
        long likeCount = likeRepository.countLikesByPostId(postId);

        assertThat(likeCount).isEqualTo(0); // Assuming no likes initially
    }

    @Test
    public void testCountLikesByCommentId() {
        long commentId = 1L;
        long likeCount = likeRepository.countLikesByCommentId(commentId);

        assertThat(likeCount).isEqualTo(0); // Assuming no likes initially
    }

    @Test
    public void testFindByPostIdAndUserId() {
        long postId = 1L;
        long userId = 1L;

        Like foundLike = likeRepository.findByPostIdAndUserId(postId, userId);

        assertThat(foundLike).isNull(); // Assuming no like exists with these IDs
    }

    @Test
    public void testFindByCommentIdAndUserId() {
        long commentId = 1L;
        long userId = 1L;

        Like foundLike = likeRepository.findByCommentIdAndUserId(commentId, userId);

        assertThat(foundLike).isNull(); // Assuming no like exists with these IDs
    }
}