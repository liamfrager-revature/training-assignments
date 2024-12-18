package com.liamfrager.connect.entity;

import java.time.LocalDateTime;
import java.util.Set;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.Formula;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;

import com.fasterxml.jackson.annotation.*;

@Data
@RequiredArgsConstructor
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name="posts")
public class Post {
    @Id
    @GeneratedValue
    @Column(name="post_id")
    private Long id;
    
    @NonNull
    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    @NonNull
    private String content;

    @Lob
    private Byte[] attachment;

    @NonNull
    @CreationTimestamp
    private LocalDateTime timestamp;

    @JsonIgnore
    @OneToMany(mappedBy="post", cascade=CascadeType.ALL, orphanRemoval=true)
    private Set<Comment> comments;

    @JsonIgnore
    @OneToMany(mappedBy="post", cascade=CascadeType.ALL, orphanRemoval=true)
    private Set<Like> likes;

    @Formula("(select count(*) from likes l where l.post_id = post_id)")
    private Long likeCount;

    @Formula("(select count(*) from comments c where c.post_id = post_id)")
    private Long commentCount;
}
