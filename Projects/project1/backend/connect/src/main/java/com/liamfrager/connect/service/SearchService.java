package com.liamfrager.connect.service;

import org.springframework.stereotype.Service;

import com.liamfrager.connect.entity.SearchResults;
import com.liamfrager.connect.repository.PostRepository;
import com.liamfrager.connect.repository.UserRepository;

/**
 * A service for handling the search query business logic.
 */
@Service
public class SearchService {
    UserRepository userRepository;
    PostRepository postRepository;

    /**
     * Constructor for the user service.
     */
    public SearchService(UserRepository userRepository, PostRepository postRepository) {
        this.userRepository = userRepository;
        this.postRepository = postRepository;
    }

    public SearchResults search(String searchQuery, Long currentUserID) {
        return new SearchResults(
            userRepository.searchByUsernameExcludingUser(searchQuery, currentUserID),
            postRepository.searchByContentExcludingUser(searchQuery, currentUserID)
        );
    }
}
