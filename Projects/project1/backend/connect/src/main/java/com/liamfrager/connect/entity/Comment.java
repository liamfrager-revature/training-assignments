package com.liamfrager.connect.entity;

import java.time.LocalDateTime;
import java.util.Set;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.Formula;

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

    @OneToMany(mappedBy="comment", cascade=CascadeType.ALL, orphanRemoval=true)
    @JsonIgnore
    private Set<Like> likes;

    @Formula("(select coalesce(count(*), 0) from likes l where l.comment_id = comment_id)")
    private Long likeCount;

    @CreationTimestamp
    private LocalDateTime timestamp;
}
