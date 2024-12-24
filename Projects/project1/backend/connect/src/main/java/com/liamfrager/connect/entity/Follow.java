package com.liamfrager.connect.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;

import com.fasterxml.jackson.annotation.JsonBackReference;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name="follow")
public class Follow {
    @Id
    @GeneratedValue(strategy=GenerationType.AUTO)
    @Column(name="follow_id")
    private Long id;

    @NonNull
    @ManyToOne
    @JsonBackReference
    @JoinColumn(name="follower_id")
    private User follower;

    @NonNull
    @ManyToOne
    @JsonBackReference
    @JoinColumn(name="followee_id")
    private User followee;

    @Override
    public String toString() {
        return "Follow{id=" + id + ", followerId=" + (follower != null ? follower.getId() : null) + ", followeeId=" + (followee != null ? followee.getId() : null) + "}";
    }
}