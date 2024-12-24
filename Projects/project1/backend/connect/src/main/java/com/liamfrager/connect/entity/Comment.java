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
@Table(name="comments")
public class Comment {
    @Id
    @GeneratedValue(strategy=GenerationType.AUTO)
    @Column(name="comment_id")
    private Long id;

    private String content;

    @ManyToOne
    @JoinColumn(name="user_id")
    private User user;

    @ManyToOne
    @JoinColumn(name="post_id")
    private Post post;

    @JsonIgnore
    @OneToMany(mappedBy="comment", cascade=CascadeType.ALL, orphanRemoval=true)
    @OnDelete(action = OnDeleteAction.CASCADE)
    private List<Like> likes;
    
    @Transient
    private Long currentUserLikeID;

    @Formula("(select coalesce(count(*), 0) from likes l where l.comment_id = comment_id)")
    private Long likeCount;

    @CreationTimestamp
    private LocalDateTime timestamp;

    public Comment(Comment comment, Long currentUserLikeID) {
        this.id = comment.id;
        this.user = comment.user;
        this.content = comment.content;
        this.timestamp = comment.timestamp;
        this.currentUserLikeID = currentUserLikeID;
        this.likeCount = comment.likeCount;
    }
}
