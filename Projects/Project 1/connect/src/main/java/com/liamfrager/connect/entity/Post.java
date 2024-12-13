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
@Table(name="posts")
public class Post {
    @NonNull
    @Id
    @GeneratedValue
    private Long id;
    
    @NonNull
    @ManyToOne
    @JoinColumn(name="user_id")
    private User user;

    @NonNull
    private String content;

    @Lob
    private byte[] attachment;

    @NonNull
    @CreationTimestamp
    private LocalDateTime timestamp;

    @OneToMany(mappedBy="post")
    private Set<Like> likes;
}
