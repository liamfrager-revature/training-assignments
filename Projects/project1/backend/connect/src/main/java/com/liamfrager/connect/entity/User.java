package com.liamfrager.connect.entity;

import java.util.HashSet;
import java.util.Set;

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
@Table(name="users")
@JsonInclude(JsonInclude.Include.NON_NULL)
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
    @JsonIgnore
    public String getPassword() {return this.password;}
    @JsonProperty
    public void setPassword(String password) {this.password = password;}

    @Lob
    private Byte[] pfp;

    @OneToMany(mappedBy="follower")
    @JsonIgnore
    private Set<Follow> following = new HashSet<>();

    @Formula("(select count(*) from follow f where f.follower_id = user_id)")
    private Long followingCount;

    @OneToMany(mappedBy="followee")
    @JsonIgnore
    private Set<Follow> followers = new HashSet<>();

    @Formula("(select count(*) from follow f where f.followee_id = user_id)")
    private Long followersCount;

    public User(String username, String email, String password) {
        this.username = username;
        this.email = email;
        this.password = password;
    }
}
