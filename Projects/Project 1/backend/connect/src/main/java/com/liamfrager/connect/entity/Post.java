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
@Table(name="posts")
public class Post {
    @Id
    @GeneratedValue
    @Column(name="post_id")
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

    @OneToMany(mappedBy="post", cascade=CascadeType.ALL, orphanRemoval=true)
    private Set<Like> likes;
}
