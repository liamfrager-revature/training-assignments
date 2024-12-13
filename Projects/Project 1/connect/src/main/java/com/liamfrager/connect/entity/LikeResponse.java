package com.liamfrager.connect.entity;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class LikeResponse {
    private long totalLikes;
    private Like currentUserLike;
}