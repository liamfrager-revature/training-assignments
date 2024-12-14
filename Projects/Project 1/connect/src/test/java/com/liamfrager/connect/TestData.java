package com.liamfrager.connect;

import java.time.LocalDateTime;

import com.liamfrager.connect.entity.Comment;
import com.liamfrager.connect.entity.Like;
import com.liamfrager.connect.entity.Post;
import com.liamfrager.connect.entity.User;

public class TestData {

    public static Post generatePost() {
        Post x = new Post(generateUser(), "Content", LocalDateTime.now());
        x.setId(1L);
        return x;
    }

    public static User generateUser() {
        User x = new User("username", "test@email.com", "password");
        x.setId(1L);
        return x;
    }

    public static Comment generateComment() {
        Comment x = new Comment("Content", generateUser(), generatePost(), LocalDateTime.now());
        x.setId(1L);
        return x;
    }

    public static Like generatePostLike() {
        Like x = new Like(generateUser(), generatePost());
        x.setId(1L);
        return x;
    }

    public static Like generateCommentLike() {
        Like x = new Like(generateUser(), generateComment());
        x.setId(1L);
        return x;
    }

    public static Post generateNewPost(User user) {
        return new Post(user, "Content", LocalDateTime.now());
    }

    public static User generateNewUser() {
        return new User("username", "test@email.com", "password");
    }

    public static Comment generateNewComment(Post post) {
        return new Comment("Content", post.getUser(), post, LocalDateTime.now());
    }

    public static Like generateNewPostLike(User user, Post post) {
        return new Like(user, post);
    }

    public static Like generateNewCommentLike(User user, Comment comment) {
        return new Like(user, comment);
    }
}
