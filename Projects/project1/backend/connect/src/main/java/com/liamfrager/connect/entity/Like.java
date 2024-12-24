package com.liamfrager.connect.entity;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NonNull;

import com.fasterxml.jackson.annotation.JsonBackReference;

@Data
@Entity
@Table(name="likes")
public class Like {
    @Id
    @GeneratedValue(strategy=GenerationType.AUTO)
    @Column(name="like_id")
    private Long id;

    @NonNull
    @ManyToOne
    @JsonBackReference
    @JoinColumn(name="user_id")
    private User user;

    @ManyToOne
    @JsonBackReference
    @JoinColumn(name="post_id", nullable=true)
    private Post post;

    @ManyToOne
    @JsonBackReference
    @JoinColumn(name="comment_id", nullable=true)
    private Comment comment;

    // CONSTRUCTORS
    public Like() {}
    public Like(User user, Post post) {
        this.user = user;
        this.post = post;
    }
    public Like(User user, Comment comment) {
        this.user = user;
        this.comment = comment;
    }
}