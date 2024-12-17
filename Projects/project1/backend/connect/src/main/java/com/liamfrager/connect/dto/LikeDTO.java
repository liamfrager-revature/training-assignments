package com.liamfrager.connect.dto;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.Data;


@Data
@NoArgsConstructor
@AllArgsConstructor
public class LikeDTO {
    private Long likesCount;
    private Boolean likedByCurrentUser;
}
