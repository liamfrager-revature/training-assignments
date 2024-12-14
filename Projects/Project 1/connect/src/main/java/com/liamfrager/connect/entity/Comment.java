package com.liamfrager.connect.entity;

import java.time.LocalDateTime;
import java.util.Set;

import org.hibernate.annotations.CreationTimestamp;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;

@Data
@RequiredArgsConstructor
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name="comments")
public class Comment {
    @Id
    @GeneratedValue(strategy=GenerationType.AUTO)
    @Column(name="comment_id")
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
