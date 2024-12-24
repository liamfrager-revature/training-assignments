package com.liamfrager.connect.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

import java.util.List;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.liamfrager.connect.TestData;
import com.liamfrager.connect.entity.Post;
import com.liamfrager.connect.entity.SearchResults;
import com.liamfrager.connect.entity.User;
import com.liamfrager.connect.repository.PostRepository;
import com.liamfrager.connect.repository.UserRepository;

@ExtendWith(MockitoExtension.class)
public class SearchServiceTest {

    @InjectMocks
    private SearchService searchService;

    @Mock
    private UserRepository userRepository;

    @Mock
    private PostRepository postRepository;

    @Test
    void testSearchWithResults() {
        String searchQuery = "n";
        Long currentUserID = 99L;

        List<User> userResults = List.of(TestData.generateUser());
        List<Post> postResults = List.of(TestData.generatePost());

        when(userRepository.searchByUsernameExcludingUser(searchQuery, currentUserID)).thenReturn(userResults);
        when(postRepository.searchByContentExcludingUser(searchQuery, currentUserID)).thenReturn(postResults);

        SearchResults results = searchService.search(searchQuery, currentUserID);

        assertEquals(userResults, results.getUsers());
        assertEquals(postResults, results.getPosts());

        verify(userRepository, times(1)).searchByUsernameExcludingUser(searchQuery, currentUserID);
        verify(postRepository, times(1)).searchByContentExcludingUser(searchQuery, currentUserID);
    }

    @Test
    void testSearchWithNoResults() {
        String searchQuery = "noresults";
        Long currentUserID = 99L;

        when(userRepository.searchByUsernameExcludingUser(searchQuery, currentUserID)).thenReturn(List.of());
        when(postRepository.searchByContentExcludingUser(searchQuery, currentUserID)).thenReturn(List.of());

        SearchResults results = searchService.search(searchQuery, currentUserID);

        assertEquals(0, results.getUsers().size());
        assertEquals(0, results.getPosts().size());

        verify(userRepository, times(1)).searchByUsernameExcludingUser(searchQuery, currentUserID);
        verify(postRepository, times(1)).searchByContentExcludingUser(searchQuery, currentUserID);
    }
}