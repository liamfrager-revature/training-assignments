package com.liamfrager.connect.entity;

import java.time.LocalDateTime;
import java.util.List;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.Formula;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import com.fasterxml.jackson.annotation.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name="posts")
public class Post {
    @Id
    @GeneratedValue
    @Column(name="post_id")
    private Long id;
    
    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    private String content;

    @Lob
    private byte[] attachment;

    @CreationTimestamp
    private LocalDateTime timestamp;

    @JsonIgnore
    @OneToMany(mappedBy="post", cascade=CascadeType.ALL, orphanRemoval=true)
    @OnDelete(action = OnDeleteAction.CASCADE)
    private List<Comment> comments;

    @JsonIgnore
    @OneToMany(mappedBy="post", cascade=CascadeType.ALL, orphanRemoval=true)
    @OnDelete(action = OnDeleteAction.CASCADE)
    private List<Like> likes;

    @Transient
    private Long currentUserLikeID;

    @Formula("(select coalesce(count(*), 0) from likes l where l.post_id = post_id)")
    private Long likeCount;

    @Formula("(select coalesce(count(*), 0) from comments c where c.post_id = post_id)")
    private Long commentCount;


    public Post(Post post, Long currentUserLikeID) {
        this.id = post.id;
        this.user = post.user;
        this.content = post.content;
        this.attachment = post.attachment;
        this.timestamp = post.timestamp;
        this.currentUserLikeID = currentUserLikeID;
        this.likeCount = post.likeCount;
        this.commentCount = post.commentCount;
    }
}
