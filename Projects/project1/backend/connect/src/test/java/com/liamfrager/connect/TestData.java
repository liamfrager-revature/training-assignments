package com.liamfrager.connect;

import java.time.LocalDateTime;
import java.util.List;

import com.liamfrager.connect.entity.Comment;
import com.liamfrager.connect.entity.Like;
import com.liamfrager.connect.entity.Post;
import com.liamfrager.connect.entity.SearchResults;
import com.liamfrager.connect.entity.User;

public class TestData {

    public static Post generatePost() {
        Post x = new Post();
        x.setUser(generateUser());
        x.setContent("Content");
        x.setTimestamp(LocalDateTime.now());
        x.setId(1L);
        return x;
    }

    public static User generateUser() {
        User x = new User("username", "test@email.com", "password");
        x.setId(1L);
        return x;
    }

    public static Comment generateComment() {
        Comment x = new Comment();
        x.setContent("Content");
        x.setUser(generateUser());
        x.setPost(generatePost());
        x.setTimestamp(LocalDateTime.now());
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
        Post x = new Post();
        x.setUser(user);
        x.setContent("Content");
        x.setTimestamp(LocalDateTime.now());
        return x;
    }

    public static User generateNewUser() {
        return new User("username", "test@email.com", "password");
    }

    public static Comment generateNewComment(Post post) {
        Comment x = new Comment();
            x.setUser(post.getUser());
            x.setContent("Content");
            x.setTimestamp(LocalDateTime.now());
            x.setPost(post);
        return x;
    }

    public static Like generateNewPostLike(User user, Post post) {
        return new Like(user, post);
    }

    public static Like generateNewCommentLike(User user, Comment comment) {
        return new Like(user, comment);
    }

    public static SearchResults generateSearchResults() {
        return new SearchResults(List.of(generateUser()), List.of(generatePost()));
    }
}
