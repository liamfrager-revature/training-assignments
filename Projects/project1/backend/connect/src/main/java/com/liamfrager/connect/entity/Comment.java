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
    @JsonBackReference
    @JoinColumn(name="user_id")
    private User user;

    @NonNull
    @ManyToOne
    @JsonBackReference
    @JoinColumn(name="post_id")
    private Post post;

    @OneToMany(mappedBy="comment", cascade=CascadeType.ALL, orphanRemoval=true)
    @JsonManagedReference
    private Set<Like> likes;

    @Formula("(select count(*) from likes l where l.comment_id = comment_id)")
    private Long likeCount;

    @NonNull
    @CreationTimestamp
    private LocalDateTime timestamp;
}
