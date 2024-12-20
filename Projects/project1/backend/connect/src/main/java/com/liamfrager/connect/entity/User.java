package com.liamfrager.connect.entity;

import java.util.ArrayList;
import java.util.List;

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
@Table(name="users")
@JsonInclude(JsonInclude.Include.NON_NULL)
public class User {
    @Id
    @GeneratedValue(strategy=GenerationType.AUTO)
    @Column(name="user_id")
    private Long id;

    @Column(unique=true)
    private String username;

    @Column(unique=true)
    private String email;

    private String password;
    @JsonIgnore
    public String getPassword() {return this.password;}
    @JsonProperty
    public void setPassword(String password) {this.password = password;}

    @Lob
    private byte[] pfp;

    @OneToMany(mappedBy="follower")
    @JsonIgnore
    private List<Follow> following = new ArrayList<>();

    @Formula("(select count(*) from follow f where f.follower_id = user_id)")
    private Long followingCount;

    @OneToMany(mappedBy="followee")
    @JsonIgnore
    private List<Follow> followers = new ArrayList<>();

    @Formula("(select count(*) from follow f where f.followee_id = user_id)")
    private Long followersCount;

    public User(String username, String email, String password) {
        this.username = username;
        this.email = email;
        this.password = password;
    }
}
