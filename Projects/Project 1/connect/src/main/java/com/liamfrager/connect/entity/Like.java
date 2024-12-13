package com.liamfrager.connect.entity;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NonNull;


@Data
@Entity
@Table(name="likes")
public class Like {
    @NonNull
    @Id
    @GeneratedValue(strategy=GenerationType.AUTO)
    private Long id;

    @NonNull
    @ManyToOne
    @JoinColumn(name="user_id")
    private User user;

    @ManyToOne
    @JoinColumn(name="post_id", nullable=true)
    private Post post;

    @ManyToOne
    @JoinColumn(name="comment_id", nullable=true)
    private Comment comment;

    // CONSTRUCTORS
    public Like(long id, User user, Post post) {
        this.id = id;
        this.user = user;
        this.post = post;
    }
    public Like(long id, User user, Comment comment) {
        this.id = id;
        this.user = user;
        this.comment = comment;
    }
}