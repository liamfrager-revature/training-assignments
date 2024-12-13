package com.liamfrager.connect.unit_tests;

import java.time.LocalDateTime;

import com.liamfrager.connect.entity.Comment;
import com.liamfrager.connect.entity.Like;
import com.liamfrager.connect.entity.Post;
import com.liamfrager.connect.entity.User;

public class TestData {

    static Post generatePost() {
        return new Post(1L, generateUser(), "Content", LocalDateTime.now());
    }

    static User generateUser() {
        return new User(1L, "username", "test@email.com", "password");
    }

    static Comment generateComment() {
        return new Comment(1L, "Content", generateUser(), generatePost(), LocalDateTime.now());
    }

    static Like generatePostLike() {
        return new Like(1L, generateUser(), generatePost());
    }

    static Like generateCommentLike() {
        return new Like(1L, generateUser(), generateComment());
    }
}
