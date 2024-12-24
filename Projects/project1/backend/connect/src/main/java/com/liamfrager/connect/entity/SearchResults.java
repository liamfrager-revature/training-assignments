package com.liamfrager.connect.entity;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class SearchResults {
        private List<User> users;
        private List<Post> posts;
}
