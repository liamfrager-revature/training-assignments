package com.liamfrager.connect.entity;

import java.util.HashSet;
import java.util.Set;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;

@Data
@RequiredArgsConstructor
@AllArgsConstructor
@Entity
@Table(name="users")
public class User {
    @NonNull
    @Id
    @GeneratedValue(strategy=GenerationType.AUTO)
    private Long id;

    @NonNull
    @Column(unique=true)
    private String username;

    @NonNull
    @Column(unique=true)
    private String email;

    @NonNull
    private String password;

    @Lob
    private byte[] pfp;

    @OneToMany(mappedBy="user", cascade=CascadeType.ALL, orphanRemoval=true)
    private Set<Post> posts;

    @ManyToMany
    @JoinTable(
        name="followers", // Name of the join table
        joinColumns=@JoinColumn(name="follower_id"), // Column for the follower
        inverseJoinColumns=@JoinColumn(name="followee_id") // Column for the followee
    )
    private Set<User> following = new HashSet<>();

    @ManyToMany(mappedBy="following")
    private Set<User> followers = new HashSet<>();
}
