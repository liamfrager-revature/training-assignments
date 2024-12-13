package com.liamfrager.connect.entity;

import java.time.LocalDateTime;
import java.util.Set;

import org.hibernate.annotations.CreationTimestamp;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;

@Data
@RequiredArgsConstructor
@AllArgsConstructor
@Entity
@Table(name="comments")
public class Comment {

    @NonNull
    @Id
    @GeneratedValue(strategy=GenerationType.AUTO)
    private Long id;

    @NonNull
    private String content;

    @NonNull
    @ManyToOne
    @JoinColumn(name="user_id")
    private User user;

    @NonNull
    @ManyToOne
    @JoinColumn(name="post_id")
    private Post post;

    @OneToMany(mappedBy="comment", cascade=CascadeType.ALL, orphanRemoval=true)
    private Set<Like> likes;

    @NonNull
    @CreationTimestamp
    private LocalDateTime timestamp;
}
