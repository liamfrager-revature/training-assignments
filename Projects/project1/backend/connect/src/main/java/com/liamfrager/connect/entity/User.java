package com.liamfrager.connect.entity;

import java.util.HashSet;
import java.util.Set;

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
@Table(name="users")
public class User {
    @Id
    @GeneratedValue(strategy=GenerationType.AUTO)
    @Column(name="user_id")
    private Long id;

    @NonNull
    @Column(unique=true)
    private String username;

    @NonNull
    @Column(unique=true)
    private String email;

    private String password;

    @Lob
    private Byte[] pfp;

    @ManyToMany
    // @JsonManagedReference
    @JsonIgnore
    @JoinTable(
        name="followers", // Name of the join table
        joinColumns=@JoinColumn(name="follower_id"), // Column for the follower
        inverseJoinColumns=@JoinColumn(name="followee_id") // Column for the followee
    )
    private Set<User> following = new HashSet<>();

    @ManyToMany(mappedBy="following")
    // @JsonBackReference
    @JsonIgnore
    private Set<User> followers = new HashSet<>();

    public User(String username, String email, String password) {
        this.username = username;
        this.email = email;
        this.password = password;
    }
}
